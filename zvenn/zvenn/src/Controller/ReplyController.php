<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Entity\Reply;
use  App\Form\ReplyType;
use App\Repository\ReclamationRepository;
use App\Repository\ReplyRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use function Symfony\Component\DependencyInjection\Loader\Configurator\service;

class ReplyController extends AbstractController
{
    /**
     * @Route("/reply", name="reply")
     */
    public function index(): Response
    {
        return $this->render('reply/index.html.twig', [
            'controller_name' => 'ReplyController',
        ]);
    }
    /**
     * @param ReplyRepository $repository
     * @return Response
     * @Route ("/afficheReply/{id}", name="afficheReply")
     */
    function Affiche( $id)
    {
        $repository = $this->getDoctrine()->getRepository( Reply::class );

        $reply = $repository->listreplybyreclamation($id);
        return $this->render( 'reply/afficheReply.html.twig',
            [
                'reply' => $reply

            ] );
    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("/addReply/{id}",name="addReply")
     */
    function AjouterReply(Request $request,ReclamationRepository $repo,$id){

        $Re=$repo->find($id);
        $reply=new Reply();
        $form=$this->createForm(ReplyType::class,$reply);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $reply->setDatereply(new \DateTime('now'));
            $reply->setReclamation($Re);
            $reply->getReclamation($Re)->setEtat('traite');
            $em=$this->getDoctrine()->getManager();
            $em->persist($reply);
            $em->flush();
            return $this->redirectToRoute('afficheR');

        }
        return $this->render('reply/addReply.html.twig',
            ['form'=>$form->createView()]);
    }
    /**
     ** @param Request $request
     * @Route("reply/updateReply/{id}",name="updateReply")
     */
    function Update(ReplyRepository $repository,$id,Request $request)
    {
        $reply=new Reply();
        $reply = $repository->find($id);
        $form = $this->createForm(ReplyType::class, $reply);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

            $reply->setDatereply(new \DateTime());
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute("afficheR");
        }
        return $this->render('reply/updateReply.html.twig',
            [
                'form' => $form->createView(),
                "form_title" => "Modifier une reponse"
            ]);
    }
    /**
     * @param $id
     * @param ReplyRepository $rep
     * @Route ("/Delete/{id}", name="delete")
     */
    function Delete($id,ReplyRepository $rep){
        $reply=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($reply);
        $em->flush();
        return $this->redirectToRoute('afficheR');

    }
}
