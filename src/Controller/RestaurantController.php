<?php

namespace App\Controller;

use App\Entity\Restaurant;
use App\Form\RestaurantType;
use App\Repository\ProduitsRepository;
use App\Repository\ReclamationRepository;
use App\Repository\ReplyRepository;
use App\Repository\RestaurantRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class RestaurantController extends AbstractController
{
    /**
     * @var RestaurantRepository
     */
    private $repository ;

    public function __construct(RestaurantRepository $repository)
    {
        $this->repository = $repository;
    }
    /**
     * @Route("/restaurant", name="restaurant")
     */
    public function index(): Response
    {
        $restaurant = $this->repository->findAll();
        return $this->render('restaurant/afficherestaurant.html.twig', compact('restaurant'));
    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("/addrestaurant", name="addrestaurant")
     */
    function Add(Request $request): Response{
        $restaurant=new Restaurant();
        $form=$this->createForm(RestaurantType::class,$restaurant);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $restaurant->setUser($this->getUser());
            $file = $form->get('imageRestaurant')->getData();
            $uploads_directory = $this->getParameter('uploads_directory');

            $filename = $restaurant->getNomRestaurant() . '.' . $file->guessExtension();
            $file->move(
                $uploads_directory,
                $filename
            );

            $status="not verified";

            $restaurant->setStatus($status);
            // On stocke l'image dans la base de données (son nom)
            $restaurant->setNomImage($filename);
            $restaurant->setImageRestaurant($filename);
            $restaurant->setCreatedAt(new \DateTime());

            $em=$this->getDoctrine()->getManager();

            $em->persist($restaurant);
            $em->flush();
            return $this->redirectToRoute('user');

        }
        return $this->render('restaurant/ajoutrestaurant.html.twig',
            [
                'restaurant' => $restaurant,
                'form'=>$form->createView()]);

    }

    /**
     * @param RestaurantRepository $repository
     * @param $id
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     * @Route ("/updaterestaurant/{id}", name="update")
     */
    function Update(RestaurantRepository $repository,$id,Request $request)
    {
        $restaurant = $repository->find($id);
        $form = $this->createForm(RestaurantType::class, $restaurant);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('imageRestaurant')->getData();
            $uploads_directory = $this->getParameter('uploads_directory');

            $filename = $restaurant->getNomRestaurant() . '.' . $file->guessExtension();
            $file->move(
                $uploads_directory,
                $filename
            );

            $restaurant->setNomImage($filename);

            $restaurant->setImageRestaurant($filename);


            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute("user");
        }
        return $this->render('restaurant/update.html.twig',
            [
                'form' => $form->createView()
            ]);
    }

    /**
     * @param $id
     * @param RestaurantRepository $rep
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route ("/delete/{id}",name="deleterestaurant")
     */
    function delete($id,RestaurantRepository $rep)
    {
        $restaurant=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($restaurant);
        $em->flush();
        return $this->redirectToRoute('restaurant');
    }

    /**
     * @param RestaurantRepository $repRestaurant
     * @param ProduitsRepository $repProduit
     * @param $id
     * @return Response
     * @Route ("produits/restaurant/{id}",name="listbyrestaurant")
     */
    function ListProduitByName(RestaurantRepository $repRestaurant,ProduitsRepository $repProduit,ReclamationRepository $repReclamation,ReplyRepository $repReply,$id){
        $restaurant=$repRestaurant->find($id);
        $produit=$repProduit->listproduitbyrestaurant($restaurant->getId());
        $reclamation=$repReclamation->listreclamationbyrestaurant($restaurant->getId());


        return $this->render("restaurant/showproduct.html.twig",
            [
                'restaurant'=>$restaurant,'produits'=>$produit,'reclamation'=>$reclamation
            ]);

    }
    /**
     * @Route("/admin/restaurants", name="restaurantdashboard")
     */
    public function listrestaurant(): Response
    {

        $restaurant = $this->repository->findAll();
        return $this->render('restaurant/backofficetemplates/indexback.html.twig', compact('restaurant'));
    }
    /**
     * @param RestaurantRepository $repository
     * @param $id
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route ("/admin/restaurants/{id}", name="updatearchive")
     */
    function UpdateArchive(RestaurantRepository $repository,$id,Request $request)
    {
        $restaurant = $repository->find($id);

        $verified='verified';
        $archived='not verified';
        $x = $restaurant->getStatus();
        if ($x=='verified') {

            $restaurant->setStatus($archived);
        }
        else
            $restaurant->setStatus($verified);



        $em = $this->getDoctrine()->getManager();
        $em->flush();
        return $this->redirectToRoute("restaurantdashboard");

    }
}
