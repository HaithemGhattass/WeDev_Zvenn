<?php


namespace App\Twig;

use App\Entity\Reply;
use Doctrine\ORM\EntityManagerInterface;
use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class RepExtension extends AbstractExtension
{
    private $em;

    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;

    }

    public function getFunctions(): array
    {
        return [
            new TwigFunction('rep', [$this, 'getReplies'])
        ];
    }

    public function getReplies()
    {
        return $this->em->getRepository(Reply::class)->findAll();
    }
}