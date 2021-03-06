package com.blakdragon.petscapeclan.models

import com.blakdragon.petscapeclan.utils.expToLevel
import java.time.LocalDateTime

class WiseOldManPlayer(
    val id: Int,
    val exp: Long,
    val ehp: Double,
    val ehb: Double,
    val updatedAt: LocalDateTime,
    val combatLevel: Int,
    val latestSnapshot: WiseOldManSnapShot
) {

    fun totalBossKc(): Int {
        return listOf(
            latestSnapshot.abyssal_sire,
            latestSnapshot.alchemical_hydra,
            latestSnapshot.barrows_chests,
            latestSnapshot.bryophyta,
            latestSnapshot.callisto,
            latestSnapshot.cerberus,
            latestSnapshot.chambers_of_xeric,
            latestSnapshot.chambers_of_xeric_challenge_mode,
            latestSnapshot.chaos_elemental,
            latestSnapshot.chaos_fanatic,
            latestSnapshot.commander_zilyana,
            latestSnapshot.corporeal_beast,
            latestSnapshot.crazy_archaeologist,
            latestSnapshot.dagannoth_prime,
            latestSnapshot.dagannoth_rex,
            latestSnapshot.dagannoth_supreme,
            latestSnapshot.deranged_archaeologist,
            latestSnapshot.general_graardor,
            latestSnapshot.giant_mole,
            latestSnapshot.grotesque_guardians,
            latestSnapshot.hespori,
            latestSnapshot.kalphite_queen,
            latestSnapshot.king_black_dragon,
            latestSnapshot.kraken,
            latestSnapshot.kreearra,
            latestSnapshot.kril_tsutsaroth,
            latestSnapshot.mimic,
            latestSnapshot.nex,
            latestSnapshot.nightmare,
            latestSnapshot.phosanis_nightmare,
            latestSnapshot.obor,
            latestSnapshot.sarachnis,
            latestSnapshot.scorpia,
            latestSnapshot.skotizo,
            latestSnapshot.tempoross,
            latestSnapshot.the_gauntlet,
            latestSnapshot.the_corrupted_gauntlet,
            latestSnapshot.theatre_of_blood,
            latestSnapshot.theatre_of_blood_hard_mode,
            latestSnapshot.thermonuclear_smoke_devil,
            latestSnapshot.tzkal_zuk,
            latestSnapshot.tztok_jad,
            latestSnapshot.venenatis,
            latestSnapshot.vetion,
            latestSnapshot.vorkath,
            latestSnapshot.wintertodt,
            latestSnapshot.zalcano,
            latestSnapshot.zulrah
        ).sumOf { it.kills }
    }

    fun allSkills(): List<WiseOldManSkill> = listOf(
        latestSnapshot.attack,
        latestSnapshot.defence,
        latestSnapshot.strength,
        latestSnapshot.hitpoints,
        latestSnapshot.ranged,
        latestSnapshot.prayer,
        latestSnapshot.magic,
        latestSnapshot.cooking,
        latestSnapshot.woodcutting,
        latestSnapshot.fletching,
        latestSnapshot.fishing,
        latestSnapshot.firemaking,
        latestSnapshot.crafting,
        latestSnapshot.smithing,
        latestSnapshot.mining,
        latestSnapshot.herblore,
        latestSnapshot.agility,
        latestSnapshot.thieving,
        latestSnapshot.slayer,
        latestSnapshot.farming,
        latestSnapshot.runecrafting,
        latestSnapshot.hunter,
        latestSnapshot.construction
    )

    fun totalSkillLevel(): Int = allSkills().sumOf { it.experience.expToLevel() }

    fun anyExpOver(amount: Int): Boolean = allSkills().any { it.experience >= amount }

    fun allSkillsOver(level: Int): Boolean = allSkills().all { it.experience.expToLevel() >= level }
}

class WiseOldManSnapShot(
    val overall: WiseOldManSkill,
    val attack: WiseOldManSkill,
    val defence: WiseOldManSkill,
    val strength: WiseOldManSkill,
    val hitpoints: WiseOldManSkill,
    val ranged: WiseOldManSkill,
    val prayer: WiseOldManSkill,
    val magic: WiseOldManSkill,
    val cooking: WiseOldManSkill,
    val woodcutting: WiseOldManSkill,
    val fletching: WiseOldManSkill,
    val fishing: WiseOldManSkill,
    val firemaking: WiseOldManSkill,
    val crafting: WiseOldManSkill,
    val smithing: WiseOldManSkill,
    val mining: WiseOldManSkill,
    val herblore: WiseOldManSkill,
    val agility: WiseOldManSkill,
    val thieving: WiseOldManSkill,
    val slayer: WiseOldManSkill,
    val farming: WiseOldManSkill,
    val runecrafting: WiseOldManSkill,
    val hunter: WiseOldManSkill,
    val construction: WiseOldManSkill,

    val abyssal_sire: WiseOldManBoss,
    val alchemical_hydra: WiseOldManBoss,
    val barrows_chests: WiseOldManBoss,
    val bryophyta: WiseOldManBoss,
    val callisto: WiseOldManBoss,
    val cerberus: WiseOldManBoss,
    val chambers_of_xeric: WiseOldManBoss,
    val chambers_of_xeric_challenge_mode: WiseOldManBoss,
    val chaos_elemental: WiseOldManBoss,
    val chaos_fanatic: WiseOldManBoss,
    val commander_zilyana: WiseOldManBoss,
    val corporeal_beast: WiseOldManBoss,
    val crazy_archaeologist: WiseOldManBoss,
    val dagannoth_prime: WiseOldManBoss,
    val dagannoth_rex: WiseOldManBoss,
    val dagannoth_supreme: WiseOldManBoss,
    val deranged_archaeologist: WiseOldManBoss,
    val general_graardor: WiseOldManBoss,
    val giant_mole: WiseOldManBoss,
    val grotesque_guardians: WiseOldManBoss,
    val hespori: WiseOldManBoss,
    val kalphite_queen: WiseOldManBoss,
    val king_black_dragon: WiseOldManBoss,
    val kraken: WiseOldManBoss,
    val kreearra: WiseOldManBoss,
    val kril_tsutsaroth: WiseOldManBoss,
    val mimic: WiseOldManBoss,
    val nex: WiseOldManBoss,
    val nightmare: WiseOldManBoss,
    val phosanis_nightmare: WiseOldManBoss,
    val obor: WiseOldManBoss,
    val sarachnis: WiseOldManBoss,
    val scorpia: WiseOldManBoss,
    val skotizo: WiseOldManBoss,
    val tempoross: WiseOldManBoss,
    val the_gauntlet: WiseOldManBoss,
    val the_corrupted_gauntlet: WiseOldManBoss,
    val theatre_of_blood: WiseOldManBoss,
    val theatre_of_blood_hard_mode: WiseOldManBoss,
    val thermonuclear_smoke_devil: WiseOldManBoss,
    val tzkal_zuk: WiseOldManBoss,
    val tztok_jad: WiseOldManBoss,
    val venenatis: WiseOldManBoss,
    val vetion: WiseOldManBoss,
    val vorkath: WiseOldManBoss,
    val wintertodt: WiseOldManBoss,
    val zalcano: WiseOldManBoss,
    val zulrah: WiseOldManBoss,

    val clue_scrolls_beginner: WiseOldManClue,
    val clue_scrolls_easy: WiseOldManClue,
    val clue_scrolls_medium: WiseOldManClue,
    val clue_scrolls_hard: WiseOldManClue,
    val clue_scrolls_elite: WiseOldManClue,
    val clue_scrolls_master: WiseOldManClue
)

class WiseOldManSkill(
    val rank: Int,
    val experience: Int,
    val ehp: Double
)

class WiseOldManBoss(
    val rank: Int,
    val kills: Int,
    val ehb: Double
)

class WiseOldManClue(
    val rank: Int,
    val score: Int
)
