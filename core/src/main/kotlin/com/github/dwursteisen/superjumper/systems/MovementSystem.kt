package com.github.dwursteisen.superjumper.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.Vector2
import com.github.dwursteisen.libgdx.ashley.get
import com.siondream.superjumper.components.MovementComponent
import com.siondream.superjumper.components.TransformComponent


class MovementSystem : IteratingSystem(Family.all(TransformComponent::class.java, MovementComponent::class.java).get()) {
    private val tmp = Vector2()

    private val tm: ComponentMapper<TransformComponent> = get()
    private val mm: ComponentMapper<MovementComponent> = get()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val pos = entity[tm]
        val mov = entity[mm]

        tmp.set(mov.accel).scl(deltaTime)
        mov.velocity.add(tmp)

        tmp.set(mov.velocity).scl(deltaTime)
        pos.pos.add(tmp.x, tmp.y, 0.0f)
    }
}