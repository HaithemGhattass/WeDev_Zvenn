<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class VendeurController extends AbstractController
{
    /**
     * @Route("/vendeur", name="app_vendeur")
     */
    public function index(): Response
    {
        return $this->render('Frontend/restaurant/vendeurpage.html.twig', [
            'controller_name' => 'VendeurController',
        ]);
    }
}
