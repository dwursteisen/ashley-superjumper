package com.github.dwursteisen.superjumper.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.libgdx.ashley.get
import com.github.dwursteisen.superjumper.World
import com.siondream.superjumper.components.MovementComponent
import com.siondream.superjumper.components.SquirrelComponent
import com.siondream.superjumper.components.TransformComponent


class SquirrelSystem : IteratingSystem(Family.all(SquirrelComponent::class.java,
        TransformComponent::class.java,
        MovementComponent::class.java).get()) {

    private val tm: ComponentMapper<TransformComponent> = get()
    private val mm: ComponentMapper<MovementComponent> = get()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val t = entity[tm]
        val mov = entity[mm]

        if (t.pos.x < SquirrelComponent.WIDTH * 0.5f) {
            t.pos.x = SquirrelComponent.WIDTH * 0.5f
            mov.velocity.x = SquirrelComponent.VELOCITY
        }
        if (t.pos.x > World.WORLD_WIDTH - SquirrelComponent.WIDTH * 0.5f) {
            t.pos.x = World.WORLD_WIDTH - SquirrelComponent.WIDTH * 0.5f
            mov.velocity.x = -SquirrelComponent.VELOCITY
        }

        t.scale.x = if (mov.velocity.x < 0.0f) Math.abs(t.scale.x) * -1.0f else Math.abs(t.scale.x)
    }
}
