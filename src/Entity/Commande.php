<?php

namespace App\Entity;

use App\Repository\CommandeRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass=CommandeRepository::class)
 */
class Commande
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     * @Assert\NotBlank(message="Le champs adressse de livraison est obligatoire * ")
     */
    private $adresseLivraison;

    /**
     * @ORM\Column(type="float")
     * @Assert\Regex("#[0-9]+(\.[0-9]{1,2})?#")
     */
    private $totalCommande;

    /**
     * @ORM\Column(type="string", length=255)
     */
    private $modeLivraison;

    /**
     * @ORM\Column(type="string", length=255, nullable=true)
     */
    private $renseignement;

    /**
     * @ORM\Column(type="boolean", nullable=true)
     */
    private $status=1;

    /**
     * @ORM\ManyToOne(targetEntity=Coupon::class, cascade={"persist", "remove"},inversedBy="commande")
     */
    private $code;

    /**
     * @ORM\ManyToOne(targetEntity=User::class, inversedBy="commandes")
     */
    private $User;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getAdresseLivraison(): ?string
    {
        return $this->adresseLivraison;
    }

    public function setAdresseLivraison(?string $adresseLivraison): self
    {
        $this->adresseLivraison = $adresseLivraison;

        return $this;
    }

    public function getTotalCommande(): ?float
    {
        return $this->totalCommande;
    }

    public function setTotalCommande(float $totalCommande): self
    {
        $this->totalCommande = $totalCommande;

        return $this;
    }

    public function getModeLivraison(): ?string
    {
        return $this->modeLivraison;
    }

    public function setModeLivraison(string $modeLivraison): self
    {
        $this->modeLivraison = $modeLivraison;

        return $this;
    }

    public function getRenseignement(): ?string
    {
        return $this->renseignement;
    }

    public function setRenseignement(?string $renseignement): self
    {
        $this->renseignement = $renseignement;

        return $this;
    }

    public function getStatus(): ?bool
    {
        return $this->status;
    }

    public function setStatus(bool $status): self
    {
        $this->status = $status;

        return $this;
    }

    public function getCode()
    {
        return $this->code;
    }
    public function setCode($code)
    {
        return $this->code=$code;
    }

    function addCodeCoupon(Coupon $code)
    {
        return $this->code->add($code);

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
