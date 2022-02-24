<?php


namespace App\Twig;

use App\Entity\Restaurant;
use Doctrine\ORM\EntityManagerInterface;
use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class RestExtension extends AbstractExtension
{
    private $em;

    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;

    }

    public function getFunctions(): array
    {
        return [
            new TwigFunction('rest', [$this, 'getRestaurants'])
        ];
    }

    public function getRestaurants()
    {
        return $this->em->getRepository(Restaurant::class)->findAll();
    }
}