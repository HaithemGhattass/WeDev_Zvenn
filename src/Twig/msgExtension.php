<?php


namespace App\Twig;

use App\Entity\Messages;
use Doctrine\ORM\EntityManagerInterface;
use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class msgExtension extends AbstractExtension
{
    private $em;

    public function __construct(EntityManagerInterface $em)
    {
        $this->em = $em;

    }

    public function getFunctions(): array
    {
        return [
            new TwigFunction('msg', [$this, 'getMessages']),
        new TwigFunction('read', [$this, 'getMessagesRead']),
            new TwigFunction('allmessages', [$this, 'getMessage']),
            new TwigFunction('nombreNotRead', [$this, 'getNombreNotRead'])

        ];
    }

    public function getMessages($id)
    {
        return $this->em->getRepository(Messages::class)->findBy(array('recipient' => $id),  array('created_at' => 'ASC'));

    }
    public function getMessagesRead($id)
    {
        $a= $this->em->getRepository(Messages::class)->findBy(array('recipient' => $id, 'is_read' => 0 ), null, null);
  return COUNT($a);
    }
    public function getNombreNotRead($id1,$id2)
    {
        $a= $this->em->getRepository(Messages::class)->findBy(array('recipient' => $id1,'sender' => $id2, 'is_read' => 0 ), null, null);
        return COUNT($a);
    }
    public function getMessage()
    {
        return  $this->em->getRepository(Messages::class)->findBy(array(), array('created_at' => 'ASC'));

    }
}