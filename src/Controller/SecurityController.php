<?php

namespace App\Controller;

use App\Form\EditProfileType;
use App\Repository\UserRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Twilio\Rest\Client;

class SecurityController extends AbstractController
{
    /**
     * @Route("/login", name="app_login")
     */
    public function login(AuthenticationUtils $authenticationUtils): Response
    {

        if ($this->getUser()) {
 $this->get('session')->clear();
            return $this->redirectToRoute('app_home_front');
        }
        // get the login error if there is one
        $error = $authenticationUtils->getLastAuthenticationError();
        // last username entered by the user
        $lastUsername = $authenticationUtils->getLastUsername();

        return $this->render('security/login.html.twig', ['last_username' => $lastUsername, 'error' => $error]);
    }

    /**
     * @Route("/logout", name="app_logout")
     */
    public function logout(): Response
    {
        throw new \LogicException('This method can be blank - it will be intercepted by the logout key on your firewall.');
    }
    /**
     * @Route ("user/profile/modifier", name="updateuserr")
     */
    function Update(Request $request)
    {
        $user =$this->getUser();
        $form = $this->createForm(EditProfileType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {


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
                return $this->redirectToRoute("app_profile");



        }
        return $this->render('Frontend/editprofile.html.twig',
            [
                'form' => $form->createView()
            ]);
    }

    /**
     * @Route ("user/modifierpass", name="updatepass")
     */
    function Updatepsw(Request $request,UserPasswordEncoderInterface $pass)
    {
        if($request->isMethod('POST')){
            $em=$this->getDoctrine()->getManager();
            $user=$this->getUser();
            if($request->request->get('pass')==$request->request->get('pass2')){


                $user->setPassword($pass->encodePassword($user,$request->request->get('pass')));
                $em->flush();
                $this->addFlash('message','mot de passe a mise a jour');
                return $this->redirectToRoute('app_profile');
            }



            }
        return $this->render('frontend/editpassw.html.twig'
        );
    }
    /**
     * @Route("/admin/backend/utilisateur", name="app_backend_utilisateur")
     */
    public function AfficheUser(UserRepository $users): Response
    {


        return $this->render('backend/tables/utilisateurs.html.twig', ['user'=>$users->findAll()]);
    }


    /**
     * @Route ("/admin/deleteuser/{id}",name="deleteuser")
     */

    function delete($id,UserRepository $rep)
    {
        $user=$rep->find($id);
        $em=$this->getDoctrine()->getManager();
        $em->remove($user);
        $em->flush();
        return $this->redirectToRoute('app_backend_utilisateur');
    }
    /**
     * @Route ("/admin/utilisateur/{id}", name="updaterole")
     */
    function UpdateRole(UserRepository $repository,$id,Request $request)
    {
        $user = $repository->find($id);
        if(in_array('ROLE_ADMIN', $user->getRoles())){

            $user->setRoles(array('ROLE_USER'));
        }

        else
            $user->setRoles(array('ROLE_ADMIN'));


        $em = $this->getDoctrine()->getManager();
        $em->flush();
        return $this->redirectToRoute("app_backend_utilisateur");

    }
    /**
     * @Route ("/admin/utilisateurEtat/{id}", name="updateetat")
     */
    function UpdateEtat(UserRepository $repository,$id,Request $request)
    {
        $user = $repository->find($id);
        if($user->getEtat()==0){
            $user->setEtat(1);
        }

        else
            $user->setEtat(0);



        $em = $this->getDoctrine()->getManager();
        $em->flush();
        return $this->redirectToRoute("app_backend_utilisateur");

    }

    /**
     * @Route ("user/verifsms", name="verifsms")
     */

    function Verifsms(Request $request)
    {

        if($request->isMethod('POST')){
            $em=$this->getDoctrine()->getManager();
            $user=$this->getUser();
            if($request->request->get('smscode')==$user->getPhoneCode()){


                $user->setIsVerifiedPhone(true);
                $em->flush();
                $this->addFlash('messages','votre phone est verifier');
                return $this->redirectToRoute('app_profile');
            }
            else{
                $this->addFlash('messages','le code est incorrect');

            }



        }
        return $this->render('frontend/verifsms.html.twig');
    }

}
