<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Form\EvenementType;
use App\Repository\EvenementRepository;
use App\Repository\RestaurantRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class EvenementController extends AbstractController
{
    /**
     * @Route("/evenement", name="app_evenement")
     */
    public function index(): Response
    {
        return $this->render('evenement/index.html.twig', [
            'controller_name' => 'EvenementController',
        ]);
    }
    /**
     * @param EvenementRepository $repository
     * @return Response
     * @Route ("/blog/evenements", name="events")
     */
    function Affiche( ){
        $repo=$this->getDoctrine()->getRepository(Evenement::class);

        $evenement=$repo->findAll();

        return $this->render('Frontend/evenement/index.html.twig',
            [
                'Evenement'=>$evenement,
            ]);
    }


    /**
     * @param Request $request
     * @param EvenementRepository $repository
     * @return Response
     * @Route ("/vendeur/add/{id}", name="add")
     */
    public function Add(Request $request , RestaurantRepository $repo,$id ): Response
    { $Re=$repo->find($id);
        $evenement = new Evenement();
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

                $file = $form->get( 'image' )->getData();
            if($file != null) {
                //  $evenement->setRestaurant($Restaurant);;
                $uploads_directory = $this->getParameter( 'uploads_directory' );
                $filename = $evenement->getTitre() . '.' . $file->guessExtension();
                $file->move(
                    $uploads_directory,
                    $filename
                );
                // On stocke l'image dans la base de données (son nom)
                $evenement->setImage( $filename );
                $evenement->setNomimage( $filename );
            }
            $evenement->setRestaurant($Re);


            // $evenement->setCreatedAt(new \DateTime());



            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($evenement);
            $entityManager->flush();

            return $this->redirectToRoute('app_vendeur');
        }

        return $this->render('Frontend/evenement/Newevent.html.twig', [
            'Evenement' => $evenement,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @param EvenementRepository $repository
     * @param $id
     * @param Request $request
     * @Route ("vendeur/edit/{id}",name="editE")
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     */

    function Update(EvenementRepository $repository,$id,Request $request)
    {
        $evenement = $repository->find($id);
        $form = $this->createForm(EvenementType::class, $evenement);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('app_vendeur');
        }

        return $this->render('Frontend/evenement/editE.html.twig', [
            'evenement' => $evenement,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @param $id
     * @param EvenementRepository $rep

     * @Route ("/Delete/{id}", name="deleteE")
     */
    function Delete($id,EvenementRepository $rep){
        $evenement=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($evenement);
        $em->flush();
        return $this->redirectToRoute('app_vendeur');

    }
}
