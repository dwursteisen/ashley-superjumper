package com.github.dwursteisen.superjumper.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.libgdx.ashley.get
import com.github.dwursteisen.superjumper.World
import com.siondream.superjumper.components.GravityComponent
import com.siondream.superjumper.components.MovementComponent


class GravitySystem : IteratingSystem(Family.all(GravityComponent::class.java, MovementComponent::class.java).get()) {
    private val mm: ComponentMapper<MovementComponent> = get()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[mm].velocity.add(World.gravity.x * deltaTime, World.gravity.y * deltaTime)
    }
}