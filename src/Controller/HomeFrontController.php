<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class HomeFrontController extends AbstractController
{
    /**
     * @Route("", name="app_home_front")
     */
    public function index(): Response
    {
        return $this->render('Frontend/index.html.twig', [
            'controller_name' => 'HomeFrontController',
        ]);
    }
}
