<?php

namespace App\Controller;

use App\Form\EditProfileType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class ProfileController extends AbstractController
{
    /**
     * @Route("/user/profile", name="app_profile")
     */
    function Update(Request $request)
    {
        $user =$this->getUser();
       $numero= $user->getNumTel();
        $oldemail= $user->getEmail();
        $form = $this->createForm(EditProfileType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {

if($form->get('numTel')->getData() != $numero){
    $user->setIsVerifiedPhone(false);
}
            if($form->get('email')->getData() != $oldemail){
                $user->setIsVerified(false);
            }
            $file = $form->get('image')->getData();
            $uploads_directory = $this->getParameter('uploads_directory');
            if ($file != null) {
                $filename = $user->getNom() . '.' . $file->guessExtension();
                $file->move(
                    $uploads_directory,
                    $filename
                );
                $user->setNomImage($filename);

                $user->setImage($filename);
            }
            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            $this->addFlash('success', 'votre profile à été modifier avec succés.');

            return $this->redirectToRoute("app_profile");



        }
        return $this->render('Frontend/profile.html.twig',
            [
                'form' => $form->createView()
            ]);
    }

}
