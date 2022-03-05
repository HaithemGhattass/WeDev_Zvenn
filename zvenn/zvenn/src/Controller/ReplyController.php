<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Entity\Reply;
use  App\Form\ReplyType;
use App\Repository\ReclamationRepository;
use App\Repository\ReplyRepository;
use phpDocumentor\Reflection\Types\Null_;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Vangrg\ProfanityBundle\Storage\ProfanitiesStorageDefault;
use function Symfony\Component\DependencyInjection\Loader\Configurator\service;
use Vangrg\ProfanityBundle\Validator\Constraints as ProfanityAssert;


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
     * @ProfanityAssert\ProfanityCheck
     */
    function Affiche( $id)
    {   $Re=new Reply();
        $description=$Re->getDescription();
        $repository = $this->getDoctrine()->getRepository( Reply::class );
        $check = $this->get('vangrg_profanity.checker');
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
    function AjouterReply(Request $request,ReclamationRepository $repo,$id,ProfanitiesStorageDefault $r){
        $Re=$repo->find($id);
        $reply=new Reply();
        $form=$this->createForm(ReplyType::class,$reply);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $reply->setDatereply(new \DateTime('now'));
            $reply->setReclamation($Re);
            $reply->getReclamation($Re)->setEtat('traite');
            $em=$this->getDoctrine()->getManager();
            $badwords = array('bad1', 'bad2', 'bad3', 'ass','zab','tahan','khamej');
            $gddgd=$r->getProfanities();
            $text = $reply->getTitre();
            $des=$reply->getDescription();

            function filterBadwords($text, array $gddgd, $replaceChar = '*') {
                return preg_replace_callback(
                    array_map(function($w) { return '/\b' . preg_quote($w, '/') . '\b/i'; }, $gddgd),
                    function($match) use ($replaceChar) { return str_repeat($replaceChar, strlen($match[0])); },
                    $text
                );
            }
            $reply->setTitre(filterBadwords($text,$gddgd,'*'));
            $reply->setDescription(filterBadwords($des,$gddgd,'*'));


            $em->persist($reply);
            $em->flush();
            return $this->redirectToRoute('listbyrestaurant',['id' => $Re->getRestaurant($id)->getId() ]);

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
            $badwords = array('bad1', 'bad2', 'bad3', 'ass','zab','tahan','khamej');

            $text = $reply->getTitre();
            $des=$reply->getDescription();

            function filterBadwords($text, array $badwords, $replaceChar = '*') {
                return preg_replace_callback(
                    array_map(function($w) { return '/\b' . preg_quote($w, '/') . '\b/i'; }, $badwords),
                    function($match) use ($replaceChar) { return str_repeat($replaceChar, strlen($match[0])); },
                    $text
                );
            }
            $reply->setTitre(filterBadwords($text,$badwords,'*'));
            $reply->setDescription(filterBadwords($des,$badwords,'*'));
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('listbyrestaurant',['id' => $reply->getReclamation($id)->getRestaurant()->getId()]);
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
     * @Route ("/DeleteReply/{id}", name="deleteReply")
     */
    function Delete($id,ReplyRepository $rep){
        $reply=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($reply);
        $em->flush();

        return $this->redirectToRoute('listbyrestaurant',['id' => $reply->getReclamation($id)->getRestaurant()->getId()]);

    }



    /**
     * @param Request $request
     * @return Response
     * @Route ("/AdminaddReply/{id}",name="replydash")
     */
    function adminReply(Request $request,ReclamationRepository $repo,$id,MailerInterface $mailer){
        $Re=$repo->find($id);
        $reply=new Reply();
        $form=$this->createForm(ReplyType::class,$reply);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $reply->setDatereply(new \DateTime('now'));
            $reply->setReclamation($Re);
            $reply->getReclamation($Re)->setEtat('traite');
            $email = (new Email())
                ->from('mohamedamine.mtar@esprit.tn')
                ->to('mtar287@gmail.com')
                //->cc('cc@example.com')
                //->bcc('bcc@example.com')
                //->replyTo('fabien@example.com')
                //->priority(Email::PRIORITY_HIGH)
                ->subject('Reclamation!')
                ->text($reply->getDescription())
                ->html('<p>See Twig integration for better HTML integration!</p>');

            $mailer->send($email);
            $em=$this->getDoctrine()->getManager();
            $em->persist($reply);
            $em->flush();
            return $this->redirectToRoute('dashboard');

        }
        return $this->render('reply/backoffice/adminaddReply.html.twig',
            ['form'=>$form->createView()]);
    }
    /**
     * @Route("/mailer", name="mailer")
     */
    public function sendEmail(MailerInterface $mailer): Response
    {
        $email = (new Email())
            ->from('hello@example.com')
            ->to('you@example.com')
            //->cc('cc@example.com')
            //->bcc('bcc@example.com')
            //->replyTo('fabien@example.com')
            //->priority(Email::PRIORITY_HIGH)
            ->subject('Time for Symfony Mailer!')
            ->text('Sending emails is fun again!')
            ->html('<p>See Twig integration for better HTML integration!</p>');

        $mailer->send($email);
        return $this->render('mailer/index.html.twig', [
            'controller_name' => 'MailerController',
        ]);
    }
}