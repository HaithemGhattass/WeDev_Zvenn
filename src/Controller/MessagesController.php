<?php

namespace App\Controller;

use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Entity\Messages;
use App\Entity\User;
use App\Form\MessageType;
use App\Repository\MessagesRepository;
use Symfony\Component\Security\Core\Security;

class MessagesController extends AbstractController
{
    /**
     * @var Security
     */
    private $security;

    public function __construct(Security $security)
    {
        $this->security = $security;
    }

    /**
     * @Route("user/messages", name="messages")
     */
    public function index(): Response
    {
        return $this->render('Frontend/messages.html.twig', [
            'controller_name' => 'MessagesController',
        ]);
    }

    /**
     * @Route("user/messages/{id}", name="messages-show")
     */
    public function read($id, MessagesRepository $msgrepository, UserRepository $repository, Request $request): Response
    {
        $Allusers = $repository->findAll();
        $user = $repository->find($id);
        $currentuser = $this->security->getUser()->getId();
        $messages = $msgrepository->findBy(array('recipient' => $currentuser, 'sender' => $id), array('created_at' => 'ASC'), null, null);
        foreach ($messages as $m) {
            $m->setIsRead("1");
        }

        $Message = new Messages();
        $form = $this->createForm(MessageType::class, $Message);
        $form->handleRequest($request);



        if ($form->isSubmitted() && $form->isValid()) {

            $Message->setSender($this->getUser());
            $Message->setRecipient($user);
            $Message->setCreatedAt(new \DateTime());
            $em = $this->getDoctrine()->getManager();

            $em->persist($Message);
            $em->flush();
            return $this->redirectToRoute('messages-show', ['id' => $user->getId()]);
            $this->addFlash("message", "Message envoyer avec succees");
        }
        else {

            if ($request->isMethod('POST')) {
                $message = $msgrepository->find($request->request->get('idmessage'));
                $message->setMessage($request->request->get('message'));

                $em = $this->getDoctrine()->getManager();
                $em->flush();
                return $this->redirectToRoute('messages-show', ['id' => $user->getId()]);
            }

        }


        return $this->render('Frontend/messages.html.twig',
            [
                'user' => $user,
                'messages' => $messages,
                'Allusers' => $Allusers,
                'form' => $form->createView()
            ]
        );
    }

    /**
     * @Route("user/messages", name="messages")
     */
    public function AfficheUser(UserRepository $users): Response
    {


        return $this->render('Frontend/readMessage.html.twig', ['getuser' => $users->findAll()]);
    }

    /**
     * @Route ("user/supprimermessage/{id}",name="suppmessage")
     */

    function supprimer($id, MessagesRepository $rep)
    {
        $message = $rep->find($id);
        $em = $this->getDoctrine()->getManager();
        $em->remove($message);
        $em->flush();
        return $this->redirectToRoute('messages-show', ['id' => $message->getRecipient()->getId()]);
    }

    /**
     * @Route ("user/deletemessage/{id}",name="deletemessage")
     */

    function delete($id, MessagesRepository $rep)
    {
        $message = $rep->find($id);
        $em = $this->getDoctrine()->getManager();
        $em->remove($message);
        $em->flush();
        return $this->redirectToRoute('messages-show', ['id' => $message->getSender()->getId()]);
    }

    /**
     * @Route("/admin/backend/messagerie", name="app_backend_messagerie")
     */
    public function listemessage(MessagesRepository $repository): Response
    {

        $message = $repository->findAll();
        return $this->render('backend/tables/messagerie.html.twig', compact('message'));
    }
    /**
     * @Route ("/admin/backend/messagerie/supprimer/{id}",name="deletemessageadmin")
     */

    function deletemsgadmin($id, MessagesRepository $rep)
    {
        $message = $rep->find($id);
        $em = $this->getDoctrine()->getManager();
        $message->setMessage("message supprimer par l'administration");

        $em->flush();
        return $this->redirectToRoute('app_backend_messagerie');
    }

}