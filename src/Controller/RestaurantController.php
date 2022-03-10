<?php

namespace App\Controller;

use App\Entity\Evenement;
use App\Entity\Restaurant;
use App\Form\RestaurantType;
use App\Repository\EvenementRepository;
use App\Repository\ProduitsRepository;
use App\Repository\ReclamationRepository;
use App\Repository\ReplyRepository;
use App\Repository\RestaurantRepository;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
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
use Dompdf\Dompdf;
use Dompdf\Options;



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
     * @Route("/restaurant", name="app_home_restaurant")
     */
    public function index(Request $request, PaginatorInterface $paginator): Response
    {
        $donnees = $this->repository->findAll();
        $restaurant = $paginator->paginate(
            $donnees, // Requête contenant les données à paginer (ici nos articles)
            $request->query->getInt('page', 1), // Numéro de la page en cours, passé dans l'URL, 1 si aucune page
            6 // Nombre de résultats par page
        );


        return $this->render('Frontend/restaurant/afficherestaurant.html.twig', compact('restaurant'));

    }



    /**
     * @param Request $request
     * @return Response
     * @Route ("vendeur/addrestaurant", name="addrestaurant")
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
            return $this->redirectToRoute('app_vendeur');

        }
        return $this->render('Frontend/restaurant/ajoutrestaurant.html.twig',
            [
                'restaurant' => $restaurant,
                'form'=>$form->createView()]);

    }

    /**
     * @param RestaurantRepository $repository
     * @param $id
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     * @Route ("vendeur/updaterestaurant/{id}", name="update")
     */
    function Update(RestaurantRepository $repository,$id,Request $request)
    {
        $restaurant = $repository->find($id);
        $form = $this->createForm(RestaurantType::class, $restaurant);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $file = $form->get('imageRestaurant')->getData();
            if ($file != null) {
                $uploads_directory = $this->getParameter('uploads_directory');

                $filename = $restaurant->getNomRestaurant() . '.' . $file->guessExtension();
                $file->move(
                    $uploads_directory,
                    $filename
                );

                $restaurant->setNomImage($filename);

                $restaurant->setImageRestaurant($filename);
            }

            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute("app_vendeur");
        }
        return $this->render('Frontend/restaurant/update.html.twig',
            [
                'form' => $form->createView()
            ]);
    }

    /**
     * @param $id
     * @param RestaurantRepository $rep
     * @return \Symfony\Component\HttpFoundation\RedirectResponse
     * @Route ("vendeur/delete/{id}",name="deleterestaurant")
     */
    function delete($id,RestaurantRepository $rep)
    {
        $restaurant=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($restaurant);
        $em->flush();
        return $this->redirectToRoute('app_vendeur');
    }

    /**
     * @param RestaurantRepository $repRestaurant
     * @param ProduitsRepository $repProduit
     * @param $id
     * @return Response
     * @Route ("/restaurant_details/{id}",name="listbyrestaurant")
     */
    function ListProduitByName(RestaurantRepository $repRestaurant,EvenementRepository $repEvenement,ProduitsRepository $repProduit,ReclamationRepository $repReclamation,ReplyRepository $repReply,$id){
        $restaurant=$repRestaurant->find($id);

        $produit=$repProduit->listproduitbyrestaurant($restaurant->getId());
        $reclamation=$repReclamation->listreclamationbyrestaurant($restaurant->getId());
        $evenement=$repEvenement->listevenementbyrestaurant($restaurant->getId());


        return $this->render("Frontend/restaurant/showproduct.html.twig",
            [
                'restaurant'=>$restaurant,'produits'=>$produit,'reclamation'=>$reclamation, 'evenement'=>$evenement
            ]);

    }


    /**
     * @Route("/admin/backend/restaurant", name="app_backend_restaurant")
     *
     */
    public function listrestaurant(): Response
    {

        $restaurant = $this->repository->findAll();
        return $this->render('backend/tables/restaurant.html.twig',compact('restaurant'));
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
        return $this->redirectToRoute("app_backend_restaurant");

    }

    /**
     * @param $id
     * @param RestaurantRepository $repository
     * @Route("vendeur/pdfrestaurant/{id}" , name="pdf")
     */
    function pdf($id,RestaurantRepository $repository)
    {
        $restaurant = $repository->find($id);
        // Configure Dompdf according to your needs
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        // Retrieve the HTML generated in our twig file
        $html = $this->renderView('Frontend/restaurant/pdf/pdf.html.twig', [
            'restaurant' => $restaurant
        ]);

        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $dompdf->stream("mypdf.pdf", [
            "Attachment" => false
        ]);

    }
    /**
     * @Route("/restaurant/categorie",name="filtre")
     */
    function SearchC(RestaurantRepository $repository,Request $request){
        $data=$request->get('addresse');
        if($data=='')
        {
            $restaddr=$repository->findAll();
            return $this->render('Frontend/restaurant/addressefiltre.html.twig',[
                'restaurant'=>$restaddr
            ]);
        }
        $restaddr=$repository->findBy(['addresse'=>$data]);
        return $this->render('Frontend/restaurant/addressefiltre.html.twig',[
            'restaurant'=>$restaddr
        ]);

    }
    /**
     * @Route("/restaurant/addresse",name="addresse")
     */
    function ASC(RestaurantRepository $repository,Request $request){
        $data=$request->get('addresse');
        if($data=='')
        {
            $restcat=$repository->findAll();
            return $this->render('Frontend/restaurant/filtre.html.twig',[
                'restaurant'=>$restcat
            ]);
        }
        $restcat=$repository->findBy(['categorieRestaurant'=>$data]);
        return $this->render('Frontend/restaurant/filtre.html.twig',[
            'restaurant'=>$restcat
        ]);

    }



    ///---------------PARTIE MOBILE ----------------------



    /******************Ajouter Reclamation*****************************************/
    /**
     * @Route("/RestaurantaddM", name="restaurantaddM")
     * @Method("POST")
     */

    public function ajouterrestaurantaction(Request $request)
    {
        $restaurant = new Restaurant();
        $nomRestaurant = $request->query->get("nomRestaurant");
        $description = $request->query->get("description");
        $addresse = $request->query->get("addresse");
        $cite = $request->query->get("cite");
        $codePostal = $request->query->get("codePostal");
        $heureOuverture =$request->query->get('heureOuverture');
        $categorieRestaurant = $request->query->get('categorieRestaurant');
        $em = $this->getDoctrine()->getManager();
        $date = new \DateTime('now');
        $status="not verified";

        $restaurant->setStatus($status);

        $restaurant->setNomRestaurant($nomRestaurant);
        $restaurant->setDescription($description);
        $restaurant->setAddresse($addresse);
        $restaurant->setCite($cite);
        $restaurant->setCodePostal($codePostal);
        $restaurant->setCreatedAt($date);
        $restaurant->setCategorieRestaurant($categorieRestaurant);
        $restaurant->setHeureOuverture(new \DateTime($heureOuverture));

        $em->persist($restaurant);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($restaurant);
        return new JsonResponse($formatted);

    }

    /******************Supprimer Restaurant*****************************************/

    /**
     * @Route("/deleteRestaurant", name="delete_restaurant")
     * @Method("DELETE")
     */

    public function deleteRestaurantAction(Request $request) {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $restaurant = $em->getRepository(Restaurant::class)->find($id);
        if($restaurant!=null ) {
            $em->remove($restaurant);
            $em->flush();

            $serialize = new Serializer([new ObjectNormalizer()]);
            $formatted = $serialize->normalize($restaurant);
            return new JsonResponse($formatted);

        }
        return new JsonResponse("id Restaurant invalide.");


    }

    /******************Modifier Restaurant*****************************************/
    /**
     * @Route("/updateRestaurant", name="update_restaurant")
     * @Method("PUT")
     */
    public function modifierRestaurantAction(Request $request) {
        $em = $this->getDoctrine()->getManager();
        $restaurant = $this->getDoctrine()->getManager()
            ->getRepository(Restaurant::class)
            ->find($request->get("id"));

        $restaurant->setNomRestaurant($request->get("nomRestaurant"));
        $restaurant->setAddresse($request->get("addresse"));
        $restaurant->setDescription($request->get("description"));

        $em->persist($restaurant);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($restaurant);
        return new JsonResponse("Restaurant a ete modifiee avec success.");

    }



    /******************affichage Reclamation*****************************************/

    /**
     * @Route("/displayRestaurant", name="display_restaurant")
     *
     */
    public function allResAction(NormalizerInterface $normalizer)
    {

        $repository = $this->getDoctrine()->getRepository(Restaurant::class);
        $restaurant =$repository->findAll();
        $jsonContent = $normalizer->normalize($restaurant, 'json',['groups'=>'post:read']);

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
        $reclamation = $this->getDoctrine()->getManager()->getRepository(Restaurant::class)->find($id);
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
