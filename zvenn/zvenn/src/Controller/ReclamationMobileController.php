<?php

namespace App\Controller;

use App\Entity\Restaurant;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use App\Entity\Reclamation;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Serializer\Serializer;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\Validator\Constraints\Json;
use App\Repository\RestaurantRepository;
use App\Repository\ReclamationRepository;

class ReclamationMobileController extends AbstractController
{
    /**
     * @Route("/reclamation/mobile", name="reclamation_mobile")
     */
    public function index(): Response
    {
        return $this->render('reclamation_mobile/index.html.twig', [
            'controller_name' => 'ReclamationMobileController',
        ]);
    }



    /******************Ajouter Reclamation*****************************************/
    /**
     * @Route("/addReclamation", name="add_reclamation")
     * @Method("POST")
     */

    public function ajouterReclamationAction(Request $request,RestaurantRepository $repository,NormalizerInterface $normalizer)
    {   $re=$repository->find(2);
        $reclamation = new Reclamation();

        $titre = $request->query->get("titre");
        $description = $request->query->get("description");
        $foodqulaite = $request->query->get("foodqulaite");
        $service = $request->query->get("service");
        $price = $request->query->get("price");
        $reclamation->setRestaurant($re);
        //$reclamation->setRestaurant($this->getDoctrine()->getManagers()->getRepository(Restaurant::class)->find($re));
        $em = $this->getDoctrine()->getManager();
        $date = new \DateTime('now');
        $reclamation->setDescription($description);
        $reclamation->setTitre($titre);
        $reclamation->setFoodqulaite($foodqulaite);
        $reclamation->setService($service);
        $reclamation->setPrice($price);

        $reclamation->setDaterec($date);

        $reclamation->setEtat("non traité");

        $em->persist($reclamation);
        $em->flush();

        $jsonContent = $normalizer->normalize($reclamation, 'json',['groups'=>'post:read']);
        return new JsonResponse($jsonContent);

    }

    /******************Supprimer Reclamation*****************************************/

    /**
     * @Route("/deleteReclamation/{id}", name="delete_reclamation")
     * @Method("DELETE")
     */

    public function deleteReclamationAction(Request $request,NormalizerInterface $normalizer) {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $reclamation = $em->getRepository(Reclamation::class)->find($id);
        if($reclamation!=null ) {
            $em->remove($reclamation);
            $em->flush();


            $jsonContent = $normalizer->normalize($reclamation, 'json',['groups'=>'post:read']);
            return new JsonResponse($jsonContent);
        }
        return new JsonResponse("id reclamation invalide.");


    }

    /******************Modifier Reclamation*****************************************/
    /**
     * @Route("/updateReclamation", name="update_reclamation")
     * @Method("PUT")
     */
    public function modifierReclamationAction(Request $request,NormalizerInterface $normalizer) {
        $em = $this->getDoctrine()->getManager();
        $reclamation = $this->getDoctrine()->getManager()
            ->getRepository(Reclamation::class)
            ->find($request->get("id"));

        $reclamation->setTitre($request->get("titre"));
        $reclamation->setDescription($request->get("description"));

        $em->persist($reclamation);
        $em->flush();
        $jsonContent = $normalizer->normalize($reclamation, 'json',['groups'=>'post:read']);
        return new JsonResponse($jsonContent);

    }



    /******************affichage Reclamation*****************************************/

    /**
     * @Route("/displayReclamations", name="display_reclamation")
     * @Method("GET")
     */
    public function allReclamAction(NormalizerInterface $normalizer)
    {


        $repository = $this->getDoctrine()->getRepository(Reclamation::class);

        $reclamation =$repository->findAll();
        $jsonContent = $normalizer->normalize($reclamation, 'json',['groups'=>'post:read']);

        return new Response(json_encode($jsonContent));


    }


    /******************Detail Reclamation*****************************************/

    /**
     * @Route("/detailReclamation", name="detail_reclamation")
     * @Method("GET")
     */

    //Detail Reclamation
    public function detailReclamationAction(Request $request)
    {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $reclamation = $this->getDoctrine()->getManager()->getRepository(Reclamation::class)->find($id);
        $encoder = new JsonEncoder();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getDescription();
        });
        $serializer = new Serializer([$normalizer], [$encoder]);
        $formatted = $serializer->normalize($reclamation);
        return new JsonResponse($formatted);
    }
}
