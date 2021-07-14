package com.blakdragon.petscapeclan.models.enums

enum class PetType(
    val label: String
) {
    AbyssalOrphan("Abyssal Orphan"),
    BabyMole("Baby Mole"),
    CallistoCub("Callisto Cub"),
    Hellpuppy("Hellpuppy"),
    IkkleHydra("Ikkle Hydra"),
    JalNibRek("Jal Nib Rek"),
    KalphitePrincess("Kalphite Princess"),
    LilZik("Lil Zik"),
    LittleNightmare("Little Nightmare"),
    Noon("Noon"),
    Olmlet("Olmlet"),
    PetChaosElemental("Pet Chaos Elemental"),
    PetDagannothPrime("Pet Dagannoth Prime"),
    PetDagannothRex("Pet Dagannoth Rex"),
    PetDagannothSupreme("Pet Dagannoth Supreme"),
    PetDarkCore("Pet Dark Core"),
    PetGeneralGraardor("Pet General Graardor"),
    PetKrilTsutsaroth("Pet K'ril Tsutsaroth"),
    PetKraken("Pet Kraken"),
    PetKreeArra("Pet Kree'Arra"),
    PetSmokeDevil("Pet Smoke Devil"),
    PetSnakeling("Pet Snakeling"),
    PetZilyana("Pet Zilyana"),
    PrinceBlackDragon("Prince Black Dragon"),
    ScorpiasOffspring("Scorpias Offspring"),
    Skotos("Skotos"),
    Sraracha("Sraracha"),
    TzrekJad("Tzrek Jad"),
    VenenatisSpiderling("Venenatis Spiderling"),
    VetionJr("Vetion Jr."),
    Vorki("Vorki"),

    BabyChinchompa("Baby Chinchompa"),
    Beaver("Beaver"),
    GiantSquirrel("Giant Squirrel"),
    Heron("Heron"),
    RiftGuardian("Rift Guardian"),
    RockGolem("Rock Golem"),
    Rocky("Rocky"),
    Tangleroot("Tangleroot"),

    Bloodhound("Bloodhound"),
    ChompyChick("Chompy Chick"),
    Herbi("Herbi"),
    LilCreator("Lil Creator"),
    PetPenanceQueen("Pet Penance Queen"),
    Phoenix("Phoenix"),
    TinyTempor("Tiny Tempor"),
    Youngllef("Youngllef"),
    Smolcano("Smolcano");

    fun toResponse(): PetTypeResponse = PetTypeResponse(this)
}

class PetTypeResponse(
    val type: PetType
) {
    val label: String = type.label
}
