package com.blakdragon.petscapeclan.utils

import com.blakdragon.petscapeclan.models.BingoGame
import com.mongodb.client.MongoDatabase
import org.bson.types.ObjectId
import kotlin.random.Random

const val BINGO_NUM_SQUARES = 25
private const val FREE_SQUARE = 12

//fun generateSquares(type: BingoGameType, freeSpace: Boolean): List<BingoSquareMongo> {
//    val squares = mutableListOf<BingoSquareMongo>()
//    val choices = getSquareChoices(type).toMutableList()
//
//    for (i in 0 until BINGO_NUM_SQUARES) {
//        val square = if (freeSpace && i == FREE_SQUARE) {
//            BingoSquareMongo.FreeSquare
//        } else {
//            val index = Random.Default.nextInt(choices.size)
//            val choice = if (type != BingoGameType.BOSSES) choices.removeAt(index) else choices[index]
//            val square = BingoSquareMongo()
//
//            when (type) {
//                BingoGameType.BOSSES -> square.boss = choice as Boss
//                BingoGameType.ITEMS -> square.item = choice as Drop
//                BingoGameType.COMBINED -> {
//                    val pair = choice as Pair<*, *>
//                    square.boss = pair.first as Boss
//                    square.item = pair.second as Drop
//                }
//                else -> Unit
//            }
//
//            square
//        }
//
//        squares.add(square)
//    }
//
//    return squares
//}

//private fun getSquareChoices(type: BingoGameType): List<Any> {
//    val bosses = Boss.values().toList()
//
//    return when (type) {
//        BingoGameType.BOSSES -> bosses
//        BingoGameType.ITEMS -> getItems(bosses)
//        BingoGameType.COMBINED -> {
//            return bosses.flatMap { boss ->
//                boss.drops.map { drop ->
//                    Pair(boss, drop)
//                }
//            }
//        }
//        else -> emptyList()
//    }
//}

//fun getItems(bosses: List<Boss>): List<Drop> {
//    val items = bosses
//        .flatMap { it.drops }
//        .map { it.first }
//    return items.distinctBy(Drop::item)
//}
