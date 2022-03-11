<?php

namespace App\Entity;

use App\Repository\ReplyRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;


/**
 * @ORM\Entity(repositoryClass=ReplyRepository::class)
 */
class Reply
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull
     *  @Assert\Length(
     *      min = 2,
     *      max = 15,
     *      minMessage = "titre Reclamation doit etre au minimum {{ limit }} characters long",
     *      maxMessage = "titre Reclamation ne doit pas passer {{ limit }} characters")
     */
    private $titre;

    /**
     * @ORM\Column(type="datetime")
     */
    private $datereply;

    /**
     * @ORM\Column(type="text")
     * @Assert\NotNull
     * @Assert\Length(
     *      min = 10,
     *      max = 200,
     *      minMessage = "description doit etre au minimum {{ limit }} characters long",
     *      maxMessage = "description ne doit pas depasser {{ limit }} characters"
     * )
     */
    private $description;

    /**
     * @ORM\ManyToOne(targetEntity=Reclamation::class, inversedBy="replies")
     * @ORM\JoinColumn(nullable=false)
     */
    private $Reclamation;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="replies")
     * @ORM\JoinColumn(nullable=false)
     */
    private $User;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitre(): ?string
    {
        return $this->titre;
    }

    public function setTitre(string $titre): self
    {
        $this->titre = $titre;

        return $this;
    }

    public function getDatereply(): ?\DateTimeInterface
    {
        return $this->datereply;
    }

    public function setDatereply(\DateTimeInterface $datereply): self
    {
        $this->datereply = $datereply;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getReclamation(): ?Reclamation
    {
        return $this->Reclamation;
    }

    public function setReclamation(?Reclamation $Reclamation): self
    {
        $this->Reclamation = $Reclamation;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->User;
    }

    public function setUser(?User $User): self
    {
        $this->User = $User;

        return $this;
    }
}
