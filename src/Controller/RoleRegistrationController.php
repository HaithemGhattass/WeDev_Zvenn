<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class RoleRegistrationController extends AbstractController
{
    /**
     * @Route("/role/registration", name="app_role_registration")
     */
    public function index(): Response
    {
        if ($this->getUser()) {
            return $this->redirectToRoute('app_home_front');
        }
        return $this->render('Frontend/roleRegistration.html.twig', [
            'controller_name' => 'RoleRegistrationController',
        ]);
    }
}
