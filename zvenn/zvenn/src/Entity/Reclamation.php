<?php

namespace App\Entity;

use App\Repository\ReclamationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;
use Vangrg\ProfanityBundle\Validator\Constraints as ProfanityAssert;



/**
 * @ORM\Entity(repositoryClass=ReclamationRepository::class)
 */
class Reclamation
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     * @Groups("post:read")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\NotNull
     * @Assert\Length(
     *      min = 2,
     *      max = 15,
     *      minMessage = "titre Reclamation doit etre au minimum {{ limit }} characters long",
     *      maxMessage = "titre Reclamation ne doit pas passer {{ limit }} characters")
     * @Groups("post:read")
     * @ProfanityAssert\ProfanityCheck
     */
    private $titre;

    /**
     * @ORM\Column(type="text")
     * @Assert\NotNull
     * @Assert\Length(
     *      min = 10,
     *      max = 200,
     *      minMessage = "description doit etre au minimum {{ limit }} characters long",
     *      maxMessage = "description ne doit pas depasser {{ limit }} characters"
     *
     * )
     * @Groups("post:read")
     * @ProfanityAssert\ProfanityCheck
     */
    private $description;

    /**
     * @ORM\Column(type="blob", nullable=true)
     * @Groups("post:read")
     */
    private $image;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Groups("post:read")
     */
    private $nomimage;

    /**
     * @ORM\Column(type="integer", nullable=true)
     * @Groups("post:read")
     */
    private $foodqulaite;

    /**
     * @ORM\Column(type="integer", nullable=true)
     * @Groups("post:read")
     */
    private $service;

    /**
     * @ORM\Column(type="integer", nullable=true)
     * @Groups("post:read")
     */
    private $price;

    /**
     * @ORM\Column(type="datetime")
     * @Groups("post:read")
     */
    private $daterec;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     */
    private $etat;

    /**
     * @ORM\ManyToOne(targetEntity=Restaurant::class, inversedBy="reclamations")
     * @ORM\JoinColumn(nullable=false)
     *
     * @Groups("post:read")
     */
    private $restaurant;

    /**
     * @ORM\OneToMany(targetEntity=Reply::class, mappedBy="Reclamation", orphanRemoval=true)
     * @Groups("post:read")
     */
    private $replies;


    public function __construct()
    {
        $this->replies = new ArrayCollection();
    }

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

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(string $description): self
    {
        $this->description = $description;

        return $this;
    }

    public function getImage()
    {
        return $this->image;
    }

    public function setImage($image): self
    {
        $this->image = $image;

        return $this;
    }

    public function getNomimage(): ?string
    {
        return $this->nomimage;
    }

    public function setNomimage(?string $nomimage): self
    {
        $this->nomimage = $nomimage;

        return $this;
    }

    public function getFoodqulaite(): ?int
    {
        return $this->foodqulaite;
    }

    public function setFoodqulaite(?int $foodqulaite): self
    {
        $this->foodqulaite = $foodqulaite;

        return $this;
    }

    public function getService(): ?int
    {
        return $this->service;
    }

    public function setService(?int $service): self
    {
        $this->service = $service;

        return $this;
    }

    public function getPrice(): ?int
    {
        return $this->price;
    }

    public function setPrice(?int $price): self
    {
        $this->price = $price;

        return $this;
    }

    public function getDaterec(): ?\DateTimeInterface
    {
        return $this->daterec;
    }

    public function setDaterec(\DateTimeInterface $daterec): self
    {
        $this->daterec = $daterec;

        return $this;
    }

    public function getEtat(): ?string
    {
        return $this->etat;
    }

    public function setEtat(string $etat): self
    {
        $this->etat = $etat;

        return $this;
    }

    public function getRestaurant(): ?Restaurant
    {
        return $this->restaurant;
    }

    public function setRestaurant(?Restaurant $restaurant): self
    {
        $this->restaurant = $restaurant;

        return $this;
    }

    /**
     * @return Collection<int, Reply>
     */
    public function getReplies(): Collection
    {
        return $this->replies;
    }

    public function addReply(Reply $reply): self
    {
        if (!$this->replies->contains($reply)) {
            $this->replies[] = $reply;
            $reply->setReclamation($this);
        }

        return $this;
    }

    public function removeReply(Reply $reply): self
    {
        if ($this->replies->removeElement($reply)) {
            // set the owning side to null (unless already changed)
            if ($reply->getReclamation() === $this) {
                $reply->setReclamation(null);
            }
        }

        return $this;
    }


}
