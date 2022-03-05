<?php

namespace App\Controller;

use App\Entity\Produits;
use App\Form\ProduitsType;
use App\Repository\ProduitsRepository;
use App\Repository\RestaurantRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProduitsController extends AbstractController
{
    /**
     * @return Response
     * @Route ("/produits",name="produits")
     */
    function Affiche( ){
        $repo=$this->getDoctrine()->getRepository(Produits::class);

        $produits=$repo->findAll();

        return $this->render('produits/index.html.twig',
            [
                'produits'=>$produits,
            ]);
    }

    /**
     * @param Request $request
     * @return Response
     * @Route ("/vendeurs/ajoutproduits/{id}",name="ajouterproduits")
     */
    function AjouterProduit(Request $request, RestaurantRepository$repository,$id ): Response
    {

        $produits = new Produits();
        $restaurant = $repository->find($id);
        $form = $this->createForm(ProduitsType::class, $produits);
            $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('imageProduit')->getData();
            $produits->setRestaurant($restaurant);

            $uploads_directory = $this->getParameter('uploads_directory');

            $filename = $produits->getNomProduit() . '.' . $file->guessExtension();
            $file->move(
                $uploads_directory,
                $filename
            );
            // On stocke l'image dans la base de données (son nom)
            $produits->setNomimageProduit($filename);

            $produits->setImageProduit($filename);
            $produits->setCreatedAt(new \DateTime());

            $em = $this->getDoctrine()->getManager();

            $em->persist($produits);
            $em->flush();
            return $this->redirectToRoute('user');

        }
        return $this->render('produits/ajoutproduits.html.twig',
            [
                'produits' => $produits,
                'form' => $form->createView()]);

    }

    /**
     * @param $id
     * @param ProduitsRepository $rep
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route ("/deleteproduit/{id}",name="deleteproduit")
     */
    function delete($id,ProduitsRepository $rep)
    {
        $produit=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($produit);
        $em->flush();
        return $this->redirectToRoute('user');
    }
    /**
     * @Route("updateproduit/{id}",name="updateproduit")
     */
    function Update(ProduitsRepository $repository,$id,Request $request)
    {
        $produit = $repository->find($id);
        $form = $this->createForm(ProduitsType::class, $produit);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

            $file = $form->get('imageProduit')->getData();
            $uploads_directory = $this->getParameter('uploads_directory');

            $filename = $produit->getNomProduit() . '.' . $file->guessExtension();
            $file->move(
                $uploads_directory,
                $filename
            );
            // On stocke l'image dans la base de données (son nom)

            $produit->setNomimageProduit($filename);

            $produit->setImageProduit($filename);


            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute("user");
        }
        return $this->render('produits/updateproduit.html.twig',
            [
                'form' => $form->createView()
            ]);
    }

}
