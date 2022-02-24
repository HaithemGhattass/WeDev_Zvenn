<?php


namespace App\Twig;

use App\Entity\User;
use Doctrine\ORM\EntityManagerInterface;
use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class UserExension extends AbstractExtension
{
    private $em;

    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;

    }

    public function getFunctions(): array
    {
        return [
            new TwigFunction('user', [$this, 'getUsers'])
        ];
    }

    public function getUsers()
    {
        return $this->em->getRepository(User::class)->findAll();
    }
}