<?php

namespace App\Entity;

use App\Repository\ReservationRepository;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=ReservationRepository::class)
 */
class Reservation
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer",nullable=false)
     */
    private $id;


    /**
     * @ORM\Column(type="date")
     * @Assert\GreaterThan("today")
     */

    private $date_reseration;

    /**
     * @ORM\Column(type="integer")
     */
    private $nb_personne;

    /**
     * @ORM\Column(type="string", length=255 )
     * @Assert\Length(
     * min=4,
     * max=50,
     * minMessage = "nom du produit doit etre au minimum {{ limit }} characters long",
     * maxMessage = "nom du produit doit etre au maximum {{ limit }} characters long")
     *
     */
    private $description;


    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="reservations")
     */
    private $User;

    /**
     * @ORM\ManyToOne(targetEntity=Evenement::class, inversedBy="reservations")
     */
    private $Evenement;



    public function getId(): ?int
    {
        return $this->id;
    }



    public function getDateReseration(): ?\DateTimeInterface
    {
        return $this->date_reseration;
    }

    public function setDateReseration(\DateTimeInterface $date_reseration): self
    {
        $this->date_reseration = $date_reseration;

        return $this;
    }

    public function getNbPersonne(): ?int
    {
        return $this->nb_personne;
    }

    public function setNbPersonne(int $nb_personne): self
    {
        $this->nb_personne = $nb_personne;

        return $this;
    }

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): self
    {
        $this->description = $description;

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

    public function getEvenement(): ?Evenement
    {
        return $this->Evenement;
    }

    public function setEvenement(?Evenement $Evenement): self
    {
        $this->Evenement = $Evenement;

        return $this;
    }




}
