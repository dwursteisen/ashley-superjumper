package com.github.dwursteisen.superjumper.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.github.dwursteisen.libgdx.ashley.*
import com.github.dwursteisen.superjumper.World
import com.siondream.superjumper.components.MovementComponent
import com.siondream.superjumper.components.PlatformComponent
import com.siondream.superjumper.components.TransformComponent

val PLATEFORM_TOUCH = 99

class PlatformSystem(eventBus: EventBus) : StateMachineSystem(eventBus, Family.all(PlatformComponent::class.java,
        StateComponent::class.java,
        TransformComponent::class.java,
        MovementComponent::class.java).get()) {

    val state: ComponentMapper<StateComponent> = get()
    val platform: ComponentMapper<PlatformComponent> = get()
    val transform: ComponentMapper<TransformComponent> = get()
    val movement: ComponentMapper<MovementComponent> = get()

    val STATE_NORMAL: EntityState = object : EntityState() {

        override fun update(entity: Entity, machine: StateMachineSystem, delta: Float) {
            val platform = entity[platform]
            if (platform.type == PlatformComponent.TYPE_MOVING) {
                val pos = entity[transform]
                val mov = entity[movement]

                if (pos.pos.x < PlatformComponent.WIDTH / 2) {
                    mov.velocity.x = -mov.velocity.x
                    pos.pos.x = PlatformComponent.WIDTH / 2
                }
                if (pos.pos.x > World.WORLD_WIDTH - PlatformComponent.WIDTH / 2) {
                    mov.velocity.x = -mov.velocity.x
                    pos.pos.x = World.WORLD_WIDTH - PlatformComponent.WIDTH / 2
                }
            }
        }
    }

    val STATE_PULVERIZING: EntityState = object : EntityState() {
        override fun update(entity: Entity, machine: StateMachineSystem, delta: Float) {
            if (state[entity].time > PlatformComponent.PULVERIZE_TIME) {
                engine.removeEntity(entity)
            }
        }
    }

    override fun describeMachine() {
        startWith(STATE_NORMAL)
        onState(STATE_NORMAL).on(PLATEFORM_TOUCH) { entity ->
            go(STATE_PULVERIZING, entity)
        }
    }
}