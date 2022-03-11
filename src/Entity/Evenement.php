<?php

namespace App\Entity;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\HttpFoundation\File\File;
use App\Repository\EvenementRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
/**
 * @ORM\Entity(repositoryClass=EvenementRepository::class)
 */
class Evenement
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;



    /**
     * @Assert\GreaterThan("today")
     * @ORM\Column(type="date", nullable=true)
     */
    private $date;

    /**
     * @ORM\Column(type="blob")
     */
    private $image;
    /**
     * @ORM\Column(type="string", length=255)
     */
    private $nomimage;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Assert\Length(
     * min=4,
     * max=50)
     */

    private $description;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $titre;

    /**
     * @ORM\Column(type="time", nullable=true)
     */
    private $time;

    /**
     * @ORM\ManyToOne(targetEntity=Restaurant::class, inversedBy="evenements")
     *
     */
    private $Restaurant;

    /**
     * @ORM\OneToMany(targetEntity=Reservation::class, mappedBy="Evenement")
     */
    private $reservations;

    public function __construct()
    {
        $this->reservations = new ArrayCollection();
    }




    public function getId(): ?int
    {
        return $this->id;
    }
    /**
     * @param mixed $id
     */
    public function setId($id): void
    {
        $this->id = $id;
    }





    public function getDate(): ?\DateTimeInterface
    {
        return $this->date;
    }

    public function setDate(\DateTimeInterface $date): self
    {
        $this->date = $date;

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

    public function getDescription(): ?string
    {
        return $this->description;
    }

    public function setDescription(?string $description): self
    {
        $this->description = $description;

        return $this;
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

    public function getTime(): ?\DateTimeInterface
    {
        return $this->time;
    }

    public function setTime(?\DateTimeInterface $time): self
    {
        $this->time = $time;

        return $this;
    }

    public function setRestaurant($Restaurant)
    {
        $this->Restaurant = $Restaurant;
    }

    public function getRestaurant(): ?Restaurant
    {
        return $this->Restaurant;
    }
    /**
     * @return mixed
     */
    public function getNomimage()
    {
        return $this->nomimage;
    }

    /**
     * @param mixed $nomimage
     */
    public function setNomimage($nomimage): void
    {
        $this->nomimage = $nomimage;
    }

    /**
     * @return Collection<int, Reservation>
     */
    public function getReservations(): Collection
    {
        return $this->reservations;
    }

    public function addReservation(Reservation $reservation): self
    {
        if (!$this->reservations->contains($reservation)) {
            $this->reservations[] = $reservation;
            $reservation->setEvenement($this);
        }

        return $this;
    }

    public function removeReservation(Reservation $reservation): self
    {
        if ($this->reservations->removeElement($reservation)) {
            // set the owning side to null (unless already changed)
            if ($reservation->getEvenement() === $this) {
                $reservation->setEvenement(null);
            }
        }

        return $this;
    }
}