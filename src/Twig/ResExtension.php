<?php


namespace App\Twig;

use App\Entity\Reservation;
use Doctrine\ORM\EntityManagerInterface;
use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class ResExtension extends AbstractExtension
{
    private $em;

    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;

    }

    public function getFunctions(): array
    {
        return [
            new TwigFunction('res', [$this, 'getReservations']),
        ];

    }

    public function getReservations()
    {
        return $this->em->getRepository(Reservation::class)->findAll();
    }

}