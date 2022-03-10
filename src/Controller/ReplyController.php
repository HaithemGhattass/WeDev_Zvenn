<?php

namespace App\Controller;

use App\Entity\Reclamation;
use App\Entity\Reply;
use  App\Form\ReplyType;
use App\Repository\ReclamationRepository;
use App\Repository\ReplyRepository;
use phpDocumentor\Reflection\Types\Null_;
use Symfony\Bridge\Twig\Mime\TemplatedEmail;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Mailer\MailerInterface;
use Symfony\Component\Mime\Email;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Vangrg\ProfanityBundle\Storage\ProfanitiesStorageDefault;
use function Symfony\Component\DependencyInjection\Loader\Configurator\service;
use Vangrg\ProfanityBundle\Validator\Constraints as ProfanityAssert;


class ReplyController extends AbstractController
{
    /**
     * @Route("user/reply", name="reply")
     */
    public function index(): Response
    {
        return $this->render('reply/index.html.twig', [
            'controller_name' => 'ReplyController',
        ]);
    }
    /**
     * @param ReplyRepository $repository
     * @return Response
     * @Route ("user/afficheReply/{id}", name="afficheReply")
     * @ProfanityAssert\ProfanityCheck
     */
    function Affiche( $id)
    {   $Re=new Reply();
        $description=$Re->getDescription();
        $repository = $this->getDoctrine()->getRepository( Reply::class );
        $check = $this->get('vangrg_profanity.checker');
        $reply = $repository->listreplybyreclamation($id);


        return $this->render( 'Frontend/Reply/afficheReply.html.twig',
            [
                'reply' => $reply

            ] );

    }
    /**
     * @param Request $request
     * @return Response
     * @Route ("user/addReply/{id}",name="addReply")
     */
    function AjouterReply(Request $request,ReclamationRepository $repo,$id,ProfanitiesStorageDefault $r){
        $Re=$repo->find($id);
        $reply=new Reply();
        $form=$this->createForm(ReplyType::class,$reply);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $reply->setUser($this->getUser());

            $reply->setDatereply(new \DateTime('now'));
            $reply->setReclamation($Re);
            $em=$this->getDoctrine()->getManager();

            $gddgd=$r->getProfanities();
            $text = $reply->getTitre();
            $des=$reply->getDescription();

            function filterBadwords($text, array $gddgd, $replaceChar = '*') {
                return preg_replace_callback(
                    array_map(function($w) { return '/\b' . preg_quote($w, '/') . '\b/i'; }, $gddgd),
                    function($match) use ($replaceChar) { return str_repeat($replaceChar, strlen($match[0])); },
                    $text
                );
            }
            $reply->setTitre(filterBadwords($text,$gddgd,'*'));
            $reply->setDescription(filterBadwords($des,$gddgd,'*'));


            $em->persist($reply);
            $em->flush();
            return $this->redirectToRoute('listbyrestaurant',['id' => $Re->getRestaurant($id)->getId() ]);

        }
        return $this->render('Frontend/Reply/addReply.html.twig',
            ['form'=>$form->createView()]);
    }
    /**
     ** @param Request $request
     * @Route("user/reply/updateReply/{id}",name="updateReply")
     */
    function Update(ReplyRepository $repository,$id,Request $request,ProfanitiesStorageDefault $r)
    {
        $reply=new Reply();
        $reply = $repository->find($id);
        $form = $this->createForm(ReplyType::class, $reply);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

            $reply->setDatereply(new \DateTime());
            $gddgd=$r->getProfanities();

            $text = $reply->getTitre();
            $des=$reply->getDescription();

            function filterBadwords($text, array $gddgd, $replaceChar = '*') {
                return preg_replace_callback(
                    array_map(function($w) { return '/\b' . preg_quote($w, '/') . '\b/i'; }, $gddgd),
                    function($match) use ($replaceChar) { return str_repeat($replaceChar, strlen($match[0])); },
                    $text
                );
            }
            $reply->setTitre(filterBadwords($text,$gddgd,'*'));
            $reply->setDescription(filterBadwords($des,$gddgd,'*'));
            $em = $this->getDoctrine()->getManager();
            $em->flush();
            return $this->redirectToRoute('listbyrestaurant',['id' => $reply->getReclamation($id)->getRestaurant()->getId()]);
        }
        return $this->render('Frontend/Reply/updateReply.html.twig',
            [
                'form' => $form->createView(),
                "form_title" => "Modifier une reponse"
            ]);
    }
    /**
     * @param $id
     * @param ReplyRepository $rep
     * @Route ("user/DeleteReply/{id}", name="deleteReply")
     */
    function Delete($id,ReplyRepository $rep){
        $reply=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($reply);
        $em->flush();

        return $this->redirectToRoute('listbyrestaurant',['id' => $reply->getReclamation($id)->getRestaurant()->getId()]);

    }



    /**
     * @param Request $request
     * @return Response
     * @Route ("admin/AdminaddReply/{id}",name="replydash")
     */
    function adminReply(Request $request,ReclamationRepository $repo,$id,MailerInterface $mailer,ProfanitiesStorageDefault $r){
        $Re=$repo->find($id);
        $reply=new Reply();
        $form=$this->createForm(ReplyType::class,$reply);
        $form->handleRequest($request);
        if($form->isSubmitted() && $form->isValid()){
            $reply->setUser($this->getUser());

            $reply->setDatereply(new \DateTime('now'));
            $reply->setReclamation($Re);
            $reply->getReclamation($Re)->setEtat('traite');
            $reply->getReclamation($Re)->setTraitepar($reply->getUser()->getPseudo());
            $gddgd=$r->getProfanities();
            $text = $reply->getTitre();
            $des=$reply->getDescription();
            function filterBadwords($text, array $gddgd, $replaceChar = '*') {
                return preg_replace_callback(
                    array_map(function($w) { return '/\b' . preg_quote($w, '/') . '\b/i'; }, $gddgd),
                    function($match) use ($replaceChar) { return str_repeat($replaceChar, strlen($match[0])); },
                    $text
                );
            }

            $reply->setTitre(filterBadwords($text,$gddgd,'*'));
            $reply->setDescription(filterBadwords($des,$gddgd,'*'));
            //mail
            $da=$reply->getDatereply();
            $d=filterBadwords($des,$gddgd,'*');
            $p=$reply->getReclamation($Re)->getUser()->getPseudo();
            $email = (new TemplatedEmail())
                ->from('zvendenn2022@gmail.com')
                ->to($reply->getReclamation($Re)->getUser()->getEmail())
                //->cc('cc@example.com')
                //->bcc('bcc@example.com')
                //->replyTo('fabien@example.com')
                //->priority(Email::PRIORITY_HIGH)
                ->subject('Reclamation!')
                ->text(filterBadwords($des,$gddgd,'*'))
                ->htmltemplate('Frontend/Reply/mail.html.twig')
                ->context([
                    'name' => $p,'description'=>$d,'date'=>$da
                ]);
            $mailer->send($email);
            $em=$this->getDoctrine()->getManager();
            $em->persist($reply);
            $em->flush();
            return $this->redirectToRoute('app_backend_reclamation');

        }
        return $this->render('backend/adminaddReply.html.twig',
            ['form'=>$form->createView()]);
    }
    /**
     * @Route("/mailer", name="mailer")
     */
    public function sendEmail(MailerInterface $mailer): Response
    {
        $email = (new Email())
            ->from('hello@example.com')
            ->to('you@example.com')
            //->cc('cc@example.com')
            //->bcc('bcc@example.com')
            //->replyTo('fabien@example.com')
            //->priority(Email::PRIORITY_HIGH)
            ->subject('Time for Symfony Mailer!')
            ->text('Sending emails is fun again!')
            ->htmltemplate('<p>See Twig integration for better HTML integration!</p>');

        $mailer->send($email);
        return $this->render('mailer/index.html.twig', [
            'controller_name' => 'MailerController',
        ]);
    }

    /**
     * @Route("admin/backend/reply", name="app_backend_reply")
     */
    public function tablereplyback(): Response
    {
        $repo=$this->getDoctrine()->getRepository(Reply::class);

        $reply=$repo->findAll();
        return $this->render('backend/tables/reply.html.twig', [
            'reply' => $reply,
        ]);
    }

}