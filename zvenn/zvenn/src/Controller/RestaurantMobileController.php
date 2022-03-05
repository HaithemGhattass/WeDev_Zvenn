<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class RestaurantMobileController extends AbstractController
{
    /**
     * @Route("/restaurant/mobile", name="restaurant_mobile")
     */
    public function index(): Response
    {
        return $this->render('restaurant_mobile/index.html.twig', [
            'controller_name' => 'RestaurantMobileController',
        ]);
    }
}

