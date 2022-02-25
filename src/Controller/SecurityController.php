<?php

namespace App\Controller;

use App\Entity\User;
use App\Form\EditProfileType;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use App\Repository\UserRepository;
use App\Controller\App\Service\SecurizerSecurizer ;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;

class SecurityController extends AbstractController
{
    /**
     * @Route("/", name="app_login")
     */
    public function login(AuthenticationUtils $authenticationUtils): Response
    {

        // get the login error if there is one
        $error = $authenticationUtils->getLastAuthenticationError();
        // last username entered by the user
        $lastUsername = $authenticationUtils->getLastUsername();
        if ($this->getUser()) {

            return $this->redirectToRoute('app_login');
        }

        return $this->render('HomeFront.html.twig', ['last_username' => $lastUsername, 'error' => $error]);
    }

    /**
     * @Route("/logout", name="app_logout")
     */
    public function logout(): void
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


            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();
            return $this->redirectToRoute("user");
        }
        return $this->render('user/profile/index.html.twig',
            [
                'form' => $form->createView()
            ]);
    }

    /**
     * @Route ("user/profile/modifierpass", name="updatepass")
     */
    function Updatepsw(Request $request,UserPasswordEncoderInterface $pass)
    {
if($request->isMethod('POST')){
$em=$this->getDoctrine()->getManager();
$user=$this->getUser();
if($request->request->get('pass')==$request->request->get('pass2'))
{
$user->setPassword($pass->encodePassword($user,$request->request->get('pass')));
    $em->flush();
$this->addFlash('message','mot de passe a mise a jour');
return $this->redirectToRoute('user');
}
else {
    $this->addFlash('error','les mdp nest sont pas identique');
}}
        return $this->render('user/profile/editpassw.html.twig'
           );
    }
    /**
     * @Route("/admin/utilisateur", name="utilisateurdashboard")
     */
    public function AfficheUser(UserRepository $users): Response
    {


        return $this->render('user/userdashboard.html.twig', ['user'=>$users->findAll()]);
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
        return $this->redirectToRoute('utilisateurdashboard');
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
        return $this->redirectToRoute("utilisateurdashboard");

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
        return $this->redirectToRoute("utilisateurdashboard");

    }
}
