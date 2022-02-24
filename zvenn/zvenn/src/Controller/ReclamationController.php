<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Form\ReclamationType;
use App\Repository\ReclamationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use function Symfony\Component\DependencyInjection\Loader\Configurator\service;

class ReclamationController extends AbstractController
{
    /**
     * @Route("/reclamation", name="reclamation")
     */
    public function index(): Response
    {
        return $this->render('reclamation/index.html.twig', [
            'controller_name' => 'ReclamationController',
        ]);
    }
    /**
     * @param ReclamationRepository $repository
     * @return Response
     * @Route ("/afficheR", name="afficheR")
     */
    function Affiche( ){
        $repo=$this->getDoctrine()->getRepository(Reclamation::class);

        $reclamation=$repo->findAll();
        return $this->render('reclamation/afficheR.html.twig',
            [
                'reclamation'=>$reclamation

            ]);
    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("/addReclamation",name="addReclamation")
     */
    function AjouterReclamation(Request $request){
        $etat="non traite";
        $reclamation=new Reclamation();
        $form=$this->createForm(ReclamationType::class,$reclamation);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){

            $file = $form->get('image')->getData();
            if($file != null) {
                $uploads_directory = $this->getParameter( 'uploads_directory' );

                $filename = $reclamation->getTitre() . '.' . $file->guessExtension();
                $file->move(
                    $uploads_directory,
                    $filename
                );
                // On stocke l'image dans la base de données (son nom)

                $reclamation->setNomimage( $filename );

                $reclamation->setImage( $filename );
            }
            $reclamation->setDaterec(new \DateTime());
            $reclamation->setEtat($etat);
            $em=$this->getDoctrine()->getManager();
            $em->persist($reclamation);
            $em->flush();
            return $this->redirectToRoute('afficheR');

        }
        return $this->render('reclamation/addReclamation.html.twig',
            ['form'=>$form->createView()]);
    }
    /**
     ** @param Request $request
     * @Route("reclmation/update/{id}",name="update")
     */
    function Update(ReclamationRepository $repository,$id,Request $request)
    {
        $reclamation=new Reclamation();
        $reclamation = $repository->find($id);
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('image')->getData();
            if($file !=null) {
                $uploads_directory = $this->getParameter( 'uploads_directory' );

                $filename = $reclamation->getTitre() . '.' . $file->guessExtension();
                $file->move(
                    $uploads_directory,
                    $filename
                );

                $reclamation->setNomimage( $filename );

                $reclamation->setImage( $filename );
            }

            $reclamation->setDaterec(new \DateTime());
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute("afficheR");
        }
        return $this->render('reclamation/update.html.twig',
            [
                'form' => $form->createView(),
                "form_title" => "Modifier une reclamation"
            ]);
    }
    /**
     * @param $id
     * @param ReclamationRepository $rep
     * @Route ("/DeleteR/{id}", name="deleteR")
     */
    function Delete($id,ReclamationRepository $rep){
        $reclamation=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($reclamation);
        $em->flush();
        return $this->redirectToRoute('afficheR');

    }
}
