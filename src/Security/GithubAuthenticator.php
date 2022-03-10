<?php

namespace App\Security;

use App\Entity\User; // your user entity
use Doctrine\ORM\EntityManagerInterface;
use KnpU\OAuth2ClientBundle\Client\ClientRegistry;
use KnpU\OAuth2ClientBundle\Security\Authenticator\SocialAuthenticator;
use Symfony\Component\Security\Core\Authentication\Token\TokenInterface;
use League\OAuth2\Client\Provider\GithubResourceOwner;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\RouterInterface;
use Symfony\Component\Security\Core\User\UserProviderInterface;

class GithubAuthenticator extends SocialAuthenticator
{
    private $clientRegistry;
    private $entityManager;
    private $router;

    public function __construct(ClientRegistry $clientRegistry, EntityManagerInterface $entityManager, RouterInterface $router)
    {
        $this->clientRegistry = $clientRegistry;
        $this->entityManager = $entityManager;
        $this->router = $router;
    }

    public function supports(Request $request)
    {
// continue ONLY if the current ROUTE matches the check ROUTE
        return $request->getPathInfo() == '/connect/github/check' && $request->isMethod('GET');
    }
    public function getCredentials(Request $request)
    {
        return $this->fetchAccessToken($this->getGithubClient());
    }
    public function getUser($credentials,UserProviderInterface $userProvider)
    {
        /** @var GithubResourceOwner $githubUser */
        $githubUser=$this->getGithubClient()->fetchUserFromToken($credentials);
        $email=$githubUser->getEmail();
        $user=$this->entityManager->getRepository(User::class)->findOneBy(['email'=>$email]);
        if (!$user){
            $user=new User();
            $user->setNom($githubUser->getName());
            $user->setPrenom($githubUser->getName());
            $user->setPseudo($githubUser->getNickname());
            $user->setEmail($githubUser->getEmail());

            $user->setAdresse($githubUser->getAdresse());

            $user->setNumTel("non");
            $user->setSexe("homme");
            $user->setDateNaissance(new \DateTime());
            $user->setDateCreation(new \DateTime());
            $user->setEtat(0);
            $user->setRoles(array('ROLE_USER'));


            $user->setNomImage("default-user-image.png");
            $user->setPassword("aaaa");
            $this->entityManager->persist($user);
            $this->entityManager->flush();

        }
        return $user;
    }
    /**
     * @return \KnpU\OAuth2ClientBundle\Client\OAuth2Client
     */
    private function getGithubClient()
    {
        return $this->clientRegistry->getClient('github');
    }
    /**
     * @param Request
     * @param \Symfony\Component\Security\Core\Exception\AuthenticationException
     *
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function start(Request $request, \Symfony\Component\Security\Core\Exception\AuthenticationException $authException=null)
    {
        return  new RedirectResponse('/home');
    }


    public function onAuthenticationFailure(Request $request, \Symfony\Component\Security\Core\Exception\AuthenticationException $exception)
    {
    }
    /**
     * @param Request
     * @param Symfony\Component\Security\Core\Authentication\Token\TokenInterface $token
     * @param string $providerKey
     */
    public function onAuthenticationSuccess(Request $request, TokenInterface $token,$providerKey): ?Response
    {
        return null;
    }
}
