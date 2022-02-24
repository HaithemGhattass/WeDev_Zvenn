<?php
namespace App\Controller;


use Symfony\Component\HttpFoundation\Response;
use Twig\Environment;
use Symfony\Component\Routing\Annotation\Route;

class HomeFrontController {

    /**
     * @var Environment
     */
    private $twig;

    public function __construct(Environment $twig)
    {
        $this->twig = $twig;
    }

    /**
     * @return Response
     * @throws \Twig\Error\LoaderError
     * @throws \Twig\Error\RuntimeError
     * @throws \Twig\Error\SyntaxError
     * @Route ("/home",name="home")
     */
    public function index(): Response
    {

        return new Response($this->twig->render('HomeFront.html.twig'));
    }
}