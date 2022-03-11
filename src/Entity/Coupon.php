<?php

namespace App\Entity;

use App\Repository\CouponRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\Common\Collections\ArrayCollection;

/**
 * @ORM\Entity(repositoryClass=CouponRepository::class)
 *  @UniqueEntity(fields={"code"}, message="Ce code coupon existe déja !")
 */
class Coupon
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="integer", unique=true,nullable=true)
     * @Assert\Length( min=4 , max=5)
     *
     */
    private $code;

    /**
     * @ORM\Column(type="date", nullable=true)
     * @Assert\GreaterThan("today")
     * @Assert\NotBlank(message="Le champs date limite est obligatoire * ")
     */
    private $dateLimite;

    /**
     * @ORM\OneToMany(targetEntity=Commande::class, cascade={"persist", "remove"}, mappedBy="code")
     */
    protected $commande;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getCode(): ?int
    {
        return $this->code;
    }

    public function setCode(int $code): self
    {
        $this->code = $code;

        return $this;
    }

    public function getDateLimite(): ?\DateTimeInterface
    {
        return $this->dateLimite;
    }

    public function setDateLimite(\DateTimeInterface $dateLimite): self
    {
        $this->dateLimite = $dateLimite;

        return $this;
    }

    public function __construct()
    {
        $this->commande = new ArrayCollection();
    }

    public function getCommande()
    {
        return $this->answers;
    }

}
