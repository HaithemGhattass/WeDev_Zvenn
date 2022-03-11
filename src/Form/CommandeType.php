<?php

namespace App\Form;

use App\Entity\Commande;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextareaType;

class CommandeType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('adresseLivraison')
            ->add('totalCommande',)
            ->add('modeLivraison',ChoiceType::class, [
                'choices'  => [
                    'Livraison à domicile' => 'Livraison à domicile',
                    'Retrait en restaurant' => 'Retrait en restaurant'

                ]])
            ->add('renseignement',TextareaType::class)
            ->add('code', CodeType::class)

        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Commande::class,
        ]);
    }
}
