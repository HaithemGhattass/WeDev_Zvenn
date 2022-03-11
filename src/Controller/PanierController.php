<?php

namespace App\Controller;

use App\Repository\ProduitsRepository;
use App\Repository\RestaurantRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Session\SessionInterface;

class PanierController extends AbstractController
{
    /***************************PANIER */
    /**
     * @Route("user/affichepanier", name="affiche")
     */
    public function index(SessionInterface $session, ProduitsRepository $produitsRepo)
    {
        //$this->get('session')->clear();
        $panier=$session->get('panier',[]);
        //dd($panier);
        $panierR=[];

        foreach($panier as $id => $quantite){
            $panierR[]=[
                'produits'=> $produitsRepo->find($id),
                'quantite'=>$quantite
            ];
        }
        //dd($panierR);

        $total=0;
        foreach ($panierR as $panier) {
            $prix_commande=$panier['produits']->getPrixProduit() * $panier['quantite'];
            $total+=$prix_commande;

        }
        $session->set('total',$total);
        //dd($session);
        //dd($panierR);
        return $this->render('Frontend/panier/index.html.twig',['items'=>$panierR, 'total'=>$total]);
    }


    /***************************AJOUTER PRODUIT DANS PANIER */
    /**
     * @Route("user/panier/{id}", name="panier")
     */
    function add($id, SessionInterface $session,ProduitsRepository $repository)
    {
        $re=$repository->find($id);

        $panier=$session->get('panier',[]);

        if(!empty($panier[$id])){
            $panier[$id]++;
        }else{
            $panier[$id]=1;
        }

        $session->set('panier', $panier);
        //  dd($session->get('panier'));
        return $this->redirectToRoute('listbyrestaurant',['id' => $re->getRestaurant()->getId()]);

    }
    /***************************SUPPRIMER PRODUIT DU PANIER */
    /**
     * @Route("user/remove/{id}", name="remove")
     */
    function remove($id, SessionInterface $session)
    {

        $panier=$session->get('panier', []);

        if(!empty($panier[$id])){
            unset($panier[$id]);
        }
        $session->set('panier', $panier);
        return $this->redirectToRoute('affiche');
    }
}
