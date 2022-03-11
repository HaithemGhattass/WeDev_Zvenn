<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Entity\Reservation;
use App\Form\ReservationType;
use App\Repository\EvenementRepository;
use App\Repository\ReservationRepository;
use App\Repository\RestaurantRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ReservationController extends AbstractController
{
    /**
     * @Route("/reservation", name="app_reservation")
     */
    public function index(): Response
    {
        return $this->render('reservation/index.html.twig', [
            'controller_name' => 'ReservationController',
        ]);
    }
    /**
     * @param ReservationRepository $repository
     * @return Response
     * @Route ("admin/backend/blog", name="affiche8")
     */
    function Affiche( ){
        $repo=$this->getDoctrine()->getRepository(Reservation::class);

        $reservation=$repo->findAll();

        return $this->render('backend/tables/evenement.html.twig', [
            'reservation' => $reservation]);
    }


    /**
     * @param Request $request
     * @param RestaurantRepository $repository
     * @param $id
     * @return Response
     * @Route ("user/ajoutreservation/{id}",name="Ajou")
     */
    public function Reservation(Request $request,EvenementRepository $repository,$id): Response
    {
        $re=$repository->find($id);

        $Reservation = new Reservation();
        $form = $this->createForm(ReservationType::class, $Reservation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $Reservation->setEvenement($re);

            $Reservation->setUser($this->getUser());
            $Reservation->setDateReseration(new \DateTime());

            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($Reservation);
            $entityManager->flush();


            return $this->redirectToRoute('app_profile');
        }


        return $this->render('FrontEnd/Reservation/New.html.twig', [
            'Reservation' => $Reservation,
            'form' => $form->createView(),
        ]);
    }



    /**
     * @param Request $request
     * @param Reservation $Reservation
     * @return Response
     * @Route ("Reservation/edit/{id}",name="edit")
     */

    function Update(ReservationRepository $repository,$id,Request $request)
    {
        $Reservation= $repository->find($id);
        $form = $this->createForm(ReservationType::class, $Reservation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $Reservation->setDateReseration(new \DateTime('now'));
            $this->getDoctrine()->getManager()->flush();


            return $this->redirectToRoute('app_profile');
        }

        return $this->render('Frontend/Reservation/edit.html.twig', [
            'Reservation' => $Reservation,
            'form' => $form->createView(),
        ]);
    }
    /**
     * @param $id
     * @param ReservationRepository $rep
     * @Route ("user/deletereservation/{id}", name="delete")
     */

    function Delete($id,ReservationRepository $rep){
        $Reservation=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($Reservation);
        $em->flush();
        return $this->redirectToRoute('app_profile');

    }
}
