package com.github.dwursteisen.superjumper.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.libgdx.ashley.get
import com.siondream.superjumper.components.BoundsComponent
import com.siondream.superjumper.components.TransformComponent


class BoundsSystem : IteratingSystem(Family.all(BoundsComponent::class.java, TransformComponent::class.java).get()) {

    private val tm: ComponentMapper<TransformComponent> = get()
    private val bm: ComponentMapper<BoundsComponent> = get()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = entity[tm]
        val bounds = entity[bm]

        bounds.bounds.x = pos.pos.x - bounds.bounds.width * 0.5f
        bounds.bounds.y = pos.pos.y - bounds.bounds.height * 0.5f
    }
}