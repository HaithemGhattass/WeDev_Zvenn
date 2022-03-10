<?php


namespace App\Twig;

use App\Entity\Produits;
use Doctrine\ORM\EntityManagerInterface;
use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class ProdExtension extends AbstractExtension
{
    private $em;

    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;

    }

    public function getFunctions(): array
    {
        return [
            new TwigFunction('prod', [$this, 'getProduits'])
        ];
    }

    public function getProduits()
    {
        return $this->em->getRepository(Produits::class)->findAll();
    }
}