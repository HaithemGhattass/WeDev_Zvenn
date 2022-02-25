<?php


namespace App\Twig;

use App\Entity\Reclamation;
use Doctrine\ORM\EntityManagerInterface;
use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class RecExtension extends AbstractExtension
{
    private $em;

    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;

    }

    public function getFunctions(): array
    {
        return [
            new TwigFunction('rec', [$this, 'getReclamations'])
        ];
    }

    public function getReclamations()
    {
        return $this->em->getRepository(Reclamation::class)->findAll();
    }
}