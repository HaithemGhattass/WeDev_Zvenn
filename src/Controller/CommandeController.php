<?php

namespace App\Controller;

use App\Entity\Commande;
use App\Form\CodeType;
use App\Form\CommandeType;
use App\Form\ModifierCommandeType;
use App\Repository\CommandeRepository;
use App\Repository\CouponRepository;
use Exception;
use Knp\Component\Pager\PaginatorInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Session\SessionInterface;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class CommandeController extends AbstractController
{
    /**
     * @Route("user/cmd", name="commande")
     */
    public function index(): Response
    {
        return $this->render('commande/index.html.twig', [
            'controller_name' => 'CommandeController',
        ]);
    }

    /**
     * @Route("user/affiche", name="affiche")
     */
    public function affiche(): Response
    {
        return $this->render('commande/index.html.twig', [
            'controller_name' => 'CommandeController',
        ]);
    }

    /****************************************EFFECTUER COMMANDE */
    /**
     * @Route("user/commander", name="commande")
     */
    function PasserCommande(Request $request, SessionInterface $session,CouponRepository $repositorycoupon){

        $panier=$session->get('total');
        //dd($panier);
        $commande=new Commande();
        $commande->setTotalCommande($panier);
        $form = $this->createForm(CommandeType::class, $commande);
        // $form->add('code', CodeType::class);
        // $code=$form->get('code')->getData();
        // $coupon=$repositorycoupon->findBy($code);
        // if ($coupon == $code){
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid()){
            $commande->setUser($this->getUser());
            $em=$this->getDoctrine()->getManager();

            $em->persist($commande);
            $em->flush();
            $this->get('session')->clear();
            return $this->redirectToRoute('liste');


            //}
        }
        // else{return $this->redirectToRoute('/home');}
        return $this->render('Frontend/commande/ajouterCommande.html.twig',['form'=>$form->createView()]);
    }


    /** CONSULTER LA LISTE DES COMMANDES */
    /**
     * @Route ("user/listeCommandes", name="liste")
     */
    function listeCommandes(CommandeRepository $repository,SessionInterface $session, Request $request,  PaginatorInterface $paginator){

        //$panier=$session->get('panier');
        $allcommandes=$repository->findAll();
        $commande=$paginator->paginate(
            $allcommandes,
            $request->query->getInt('page',1),
            5
        );
        return $this->render('Frontend/commande/listeCommandes.html.twig',['commande'=>$commande]);

    }


    /****************************************MODIFIER COMMANDE */

    /**
     * @Route("user/updatecommande/{id}",name="updatecommande")
     */
    function ModifierCommande(CommandeRepository $repository, Request $request, $id){
        //DEPEND DU STATUS DE LA COMMANDE
        $commande=$repository->find($id);
        $form = $this->createForm(ModifierCommandeType::class, $commande);
        $form->handleRequest($request);
        if($form->isSubmitted()&& $form->isValid()){
            $em=$this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute("affiche");

        }
        return $this->render('Frontend/commande/modifierCommande.html.twig',['form'=>$form->createView()]);
    }


    /****************************************ANNULER COMMANDE */
    /**
     * @Route ("user/deletecommande/{id}", name="delete")
     */
    function Delete($id,CommandeRepository $rep){
        $commande=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($commande);
        $em->flush();
        return $this->redirectToRoute('liste');

    }
}
