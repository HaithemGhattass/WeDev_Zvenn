<?php

namespace App\Entity;

use App\Repository\RestaurantRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface;
use Symfony\Component\Serializer\Annotation\Groups ;
use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass=RestaurantRepository::class)
 */
class Restaurant
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
     * @Assert\Length(
     *      min = 2,
     *      max = 15,
     *      minMessage = "nom Restaurant doit etre au minimum {{ limit }} characters long",
     *      maxMessage = "nom Restaurant ne doit pas passer {{ limit }} characters")
     * @Assert\NotNull
     * @Groups("post:read")
     *
     */
    private $nomRestaurant;



    /**
     * @ORM\Column(type="blob")
     * @Groups("restaurant")
     */
    private $imageRestaurant;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     */
    private $nomImage;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     */
    private $categorieRestaurant;

    /**
     * @ORM\Column(type="string", length=255)
     * @Assert\Length(
     *      min = 5,
     *      max = 20,
     *      minMessage = "adresse doit etre au minimum {{ limit }} characters long",
     *      maxMessage = "adresse ne doit pas depasser {{ limit }} characters"
     * )
     * @Assert\NotNull
     * @Groups("post:read")
     *
     */
    private $addresse;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     *
     */
    private $cite;

    /**
     * @ORM\Column(type="integer")
     * @Assert\Range(
     *      min = 1000,
     *      max = 9999,
     *      notInRangeMessage = "code postal doit etre en 4 chiffres")
     * @Groups("post:read")
     */
    private $codePostal;

    /**
     * @ORM\Column(type="datetime")
     * @Groups("post:read")
     */
    private $createdAt;

    /**
     * @ORM\Column(type="time")
     * @Groups("post:read")
     *
     *
     */
    private $heureOuverture;

    /**
     * @ORM\Column(type="time")
     * @Assert\GreaterThan(propertyPath="heureOuverture")
     * @Groups("post:read")
     *
     */
    private $heureFermeture;

    /**
     * @ORM\Column(type="string", length=255)
     * @Groups("post:read")
     */
    private $status;

    /**
     * @ORM\Column(type="text")
     * @Assert\Length(
     *      min = 10,
     *      max = 200,
     *      minMessage = "description doit etre au minimum {{ limit }} characters long",
     *      maxMessage = "description ne doit pas depasser {{ limit }} characters"
     * )
     * @Assert\NotNull
     * @Groups("post:read")
     *
     */
    private $description;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="restaurants")
     * @Groups("post:read")
     */
    private $user;

    /**
     * @ORM\OneToMany(targetEntity=Produits::class, mappedBy="restaurant", orphanRemoval=true)
     * @Groups("post:produits")
     */
    private $produits;

    /**
     * @ORM\OneToMany(targetEntity=Reclamation::class, mappedBy="restaurant", orphanRemoval=true)
     * @Groups("post:read")
     */
    private $reclamations;

    protected $captchaCode;



    /**
     * @ORM\OneToMany(targetEntity=Evenement::class, mappedBy="Restaurant")
     */
    private $evenements;




    public function __construct()
    {
        $this->produits = new ArrayCollection();
        $this->reclamations = new ArrayCollection();

        $this->evenements = new ArrayCollection();

    }

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getNomRestaurant(): ?string
    {
        return $this->nomRestaurant;
    }

    public function setNomRestaurant(string $nomRestaurant): self
    {
        $this->nomRestaurant = $nomRestaurant;

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

    public function getImageRestaurant()
    {
        return $this->imageRestaurant;
    }

    public function setImageRestaurant($imageRestaurant): self
    {
        $this->imageRestaurant = $imageRestaurant;

        return $this;
    }

    public function getNomImage(): ?string
    {
        return $this->nomImage;
    }

    public function setNomImage(string $nomImage): self
    {
        $this->nomImage = $nomImage;

        return $this;
    }

    public function getCategorieRestaurant(): ?string
    {
        return $this->categorieRestaurant;
    }

    public function setCategorieRestaurant(string $categorieRestaurant): self
    {
        $this->categorieRestaurant = $categorieRestaurant;

        return $this;
    }

    public function getAddresse(): ?string
    {
        return $this->addresse;
    }

    public function setAddresse(string $addresse): self
    {
        $this->addresse = $addresse;

        return $this;
    }

    public function getCite(): ?string
    {
        return $this->cite;
    }

    public function setCite(string $cite): self
    {
        $this->cite = $cite;

        return $this;
    }

    public function getCodePostal(): ?int
    {
        return $this->codePostal;
    }

    public function setCodePostal(int $codePostal): self
    {
        $this->codePostal = $codePostal;

        return $this;
    }

    public function getCreatedAt(): ?\DateTimeInterface
    {
        return $this->createdAt;
    }

    public function setCreatedAt(\DateTimeInterface $createdAt): self
    {
        $this->createdAt = $createdAt;

        return $this;
    }

    public function getHeureOuverture(): ?\DateTimeInterface
    {
        return $this->heureOuverture;
    }

    public function setHeureOuverture(\DateTimeInterface $heureOuverture): self
    {
        $this->heureOuverture = $heureOuverture;

        return $this;
    }

    public function getHeureFermeture(): ?\DateTimeInterface
    {
        return $this->heureFermeture;
    }

    public function setHeureFermeture(\DateTimeInterface $heureFermeture): self
    {
        $this->heureFermeture = $heureFermeture;

        return $this;
    }

    public function getStatus(): ?string
    {
        return $this->status;
    }

    public function setStatus(string $status): self
    {
        $this->status = $status;

        return $this;
    }

    public function getUser(): ?User
    {
        return $this->user;
    }

    public function setUser(?User $user): self
    {
        $this->user = $user;

        return $this;
    }

    /**
     * @return Collection<int, Produits>
     */
    public function getProduits(): Collection
    {
        return $this->produits;
    }

    public function addProduit(Produits $produit): self
    {
        if (!$this->produits->contains($produit)) {
            $this->produits[] = $produit;
            $produit->setRestaurant($this);
        }

        return $this;
    }

    public function removeProduit(Produits $produit): self
    {
        if ($this->produits->removeElement($produit)) {
            // set the owning side to null (unless already changed)
            if ($produit->getRestaurant() === $this) {
                $produit->setRestaurant(null);
            }
        }

        return $this;
    }

    /**
     * @return Collection<int, Reclamation>
     */
    public function getReclamations(): Collection
    {
        return $this->reclamations;
    }

    public function addReclamation(Reclamation $reclamation): self
    {
        if (!$this->reclamations->contains($reclamation)) {
            $this->reclamations[] = $reclamation;
            $reclamation->setRestaurant($this);
        }

        return $this;
    }

    public function removeReclamation(Reclamation $reclamation): self
    {
        if ($this->reclamations->removeElement($reclamation)) {
            // set the owning side to null (unless already changed)
            if ($reclamation->getRestaurant() === $this) {
                $reclamation->setRestaurant(null);
            }
        }

        return $this;
    }
    public function getCaptchaCode()
    {
        return$this->captchaCode;
    }

    /**
     * @param mixed $captchaCode
     */
    public function setCaptchaCode($captchaCode): void
    {
        $this->captchaCode = $captchaCode;
    }



    /**
     * @return Collection<int, Evenement>
     */
    public function getEvenements(): Collection
    {
        return $this->evenements;
    }

    public function addEvenement(Evenement $evenement): self
    {
        if (!$this->evenements->contains($evenement)) {
            $this->evenements[] = $evenement;
            $evenement->setRestaurant($this);
        }

        return $this;
    }

    public function removeEvenement(Evenement $evenement): self
    {
        if ($this->evenements->removeElement($evenement)) {
            // set the owning side to null (unless already changed)
            if ($evenement->getRestaurant() === $this) {
                $evenement->setRestaurant(null);
            }
        }

        return $this;
    }






}
