<?php


namespace App\Twig;

use App\Entity\Evenement;
use Doctrine\ORM\EntityManagerInterface;
use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class eveExtension extends AbstractExtension
{
    private $em;

    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;

    }

    public function getFunctions(): array
    {
        return [
            new TwigFunction('eve', [$this, 'getEvenements']),
        ];

    }

    public function getEvenements()
    {
        return $this->em->getRepository(Evenement::class)->findAll();
    }

}