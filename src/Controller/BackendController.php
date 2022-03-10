<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\RestaurantRepository;


class BackendController extends AbstractController
{



    /**
     * @Route("/admin/backend", name="app_backend")
     */
    public function index(): Response
    {
        return $this->render('backend/index.html.twig', [
            'controller_name' => 'BackendController',
        ]);
    }




    /**
     * @Route("/admin/backend/produit", name="app_backend_produit")
     */
    public function tableauproduit(): Response
    {
        return $this->render('backend/tables/produits.html.twig', [
            'controller_name' => 'BackendController',
        ]);
    }
    /**
     * @Route("/admin/backend/reclamation", name="app_backend_reclamation")
     */
    public function tableaureclamation(): Response
    {
        return $this->render('backend/tables/reclamation.html.twig', [
            'controller_name' => 'BackendController',
        ]);
    }
    /**
     * @Route("/admin/backend/blog", name="app_backend_blog")
     */
    public function tableaublog(): Response
    {
        return $this->render('backend/tables/evenement.html.twig', [
            'controller_name' => 'BackendController',
        ]);
    }
    /**
     * @Route("/admin/backend/reservation", name="app_backend_reservation")
     */
    public function tableaureservation(): Response
    {
        return $this->render('backend/tables/reservation.html.twig', [
            'controller_name' => 'BackendController',
        ]);
    }

    /**
     * @Route("/admin/backend/coupon", name="app_backend_coupon")
     */
    public function tableaureduction(): Response
    {
        return $this->render('backend/tables/reduction.html.twig', [
            'controller_name' => 'BackendController',
        ]);
    }

    /**
     * @Route("/admin/backend/commande", name="app_backend_commande")
     */
    public function tableaucommande(): Response
    {
        return $this->render('backend/tables/commandes.html.twig', [
            'controller_name' => 'BackendController',
        ]);
    }

}
