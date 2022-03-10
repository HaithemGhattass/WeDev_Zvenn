<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Form\ReclamationType;
use App\Repository\ReclamationRepository;
use App\Repository\RestaurantRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;

use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\Validator\Constraints\Json;
use symfony\Component\Serializer\Annotation\Groups;
use Knp\Component\Pager\PaginatorInterface;
use function Symfony\Component\DependencyInjection\Loader\Configurator\service;

class ReclamationController extends AbstractController
{
    /**
     * @Route("/admin/reclamation", name="adminreclamation")
     */
    public function index(): Response
    {
        return $this->render('reclamation/backofficetemplates/indexback.html.twig', [
            'controller_name' => 'ReclamationController',
        ]);
    }
    /**
     * @param ReclamationRepository $repository
     * @return Response
     * @Route ("/afficheR", name="afficheR")
     */
    function Affiche( ){
        $repo=$this->getDoctrine()->getRepository(Reclamation::class);

        $reclamation=$repo->findAll();
        return $this->render('reclamation/afficheR.html.twig',
            [
                'reclamation'=>$reclamation

            ]);
    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("user/addReclamation/{id}",name="addReclamation")
     */
    function AjouterReclamation(Request $request,RestaurantRepository $repository,$id){
        $re=$repository->find($id);
        $etat="non traite";

        $reclamation=new Reclamation();

        $form=$this->createForm(ReclamationType::class,$reclamation);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $reclamation->setUser($this->getUser());

            $file = $form->get('image')->getData();
            if($file != null) {
                $uploads_directory = $this->getParameter( 'uploads_directory' );

                $filename = $reclamation->getTitre() . '.' . $file->guessExtension();
                $file->move(
                    $uploads_directory,
                    $filename
                );
                // On stocke l'image dans la base de données (son nom)

                $reclamation->setNomimage( $filename );

                $reclamation->setImage( $filename );
            }
            $reclamation->setRestaurant($re);

            $reclamation->setDaterec(new \DateTime());
            $reclamation->setEtat($etat);
            $reclamation->setTraitepar("en cours");

            $badwords = array('bad1', 'bad2', 'bad3',);
            $text = $reclamation->getTitre();
            $des=$reclamation->getDescription();

            function filterBadwords($text, array $badwords, $replaceChar = '*') {
                return preg_replace_callback(
                    array_map(function($w) { return '/\b' . preg_quote($w, '/') . '\b/i'; }, $badwords),
                    function($match) use ($replaceChar) { return str_repeat($replaceChar, strlen($match[0])); },
                    $text
                );
            }
            $reclamation->setTitre(filterBadwords($text,$badwords,'*'));
            $reclamation->setDescription(filterBadwords($des,$badwords,'*'));
            $em=$this->getDoctrine()->getManager();

            $em->persist($reclamation);
            $em->flush();
            return $this->redirectToRoute('listbyrestaurant',['id' => $re->getId()]);

        }
        return $this->render('Frontend/reclamation/addReclamation.html.twig',
            ['form'=>$form->createView()]);
    }
    /**
     ** @param Request $request
     * @Route("user/reclmation/update/{id}",name="updaterec")
     */
    function Update(ReclamationRepository $repository,$id,Request $request)
    {
        $reclamation=new Reclamation();
        $reclamation = $repository->find($id);
        $form = $this->createForm(ReclamationType::class, $reclamation);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('image')->getData();
            if($file !=null) {
                $uploads_directory = $this->getParameter( 'uploads_directory' );

                $filename = $reclamation->getTitre() . '.' . $file->guessExtension();
                $file->move(
                    $uploads_directory,
                    $filename
                );

                $reclamation->setNomimage( $filename );

                $reclamation->setImage( $filename );
            }

            $reclamation->setDaterec(new \DateTime());
            $badwords = array('bad1', 'bad2', 'bad3');
            $text = $reclamation->getTitre();
            $des=$reclamation->getDescription();

            function filterBadwords($text, array $badwords, $replaceChar = '*') {
                return preg_replace_callback(
                    array_map(function($w) { return '/\b' . preg_quote($w, '/') . '\b/i'; }, $badwords),
                    function($match) use ($replaceChar) { return str_repeat($replaceChar, strlen($match[0])); },
                    $text
                );
            }
            $reclamation->setTitre(filterBadwords($text,$badwords,'*'));
            $reclamation->setDescription(filterBadwords($des,$badwords,'*'));
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('listbyrestaurant',['id' => $reclamation->getRestaurant($id)->getId() ]);

        }
        return $this->render('Frontend/reclamation/updatereclam.html.twig',
            [
                'form' => $form->createView(),
                "form_title" => "Modifier une reclamation"
            ]);
    }
    /**
     * @param $id
     * @param ReclamationRepository $rep
     * @Route ("user/DeleteR/{id}", name="deleteR")
     */
    function Delete($id,ReclamationRepository $rep,RestaurantRepository $repository){
        $re=$repository->find($id);
        $reclamation=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($reclamation);
        $em->flush();
        return $this->redirectToRoute('listbyrestaurant',['id' => $reclamation->getRestaurant($id)->getId() ]);

    }
}