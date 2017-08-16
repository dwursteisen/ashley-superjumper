package com.github.dwursteisen.superjumper.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.github.dwursteisen.libgdx.ashley.*
import com.github.dwursteisen.superjumper.GameEvents
import com.github.dwursteisen.superjumper.World
import com.siondream.superjumper.components.BobComponent
import com.siondream.superjumper.components.MovementComponent
import com.siondream.superjumper.components.TransformComponent


class BobSystem(private val world: World, eventBus: EventBus) : StateMachineSystem(eventBus, family) {

    companion object {
        private val family = Family.all(BobComponent::class.java,
                StateComponent::class.java,
                TransformComponent::class.java,
                MovementComponent::class.java).get()
    }

    private var accelX = 0.0f

    private val bm: ComponentMapper<BobComponent> = get()
    private val sm: ComponentMapper<StateComponent> = get()
    private val tm: ComponentMapper<TransformComponent> = get()
    private val mm: ComponentMapper<MovementComponent> = get()

    fun setAccelX(accelX: Float) {
        this.accelX = accelX
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        accelX = 0.0f
    }

    override fun describeMachine() {
        val STATE_JUMP = object : EntityState() {

            override fun enter(entity: Entity, machine: StateMachineSystem, eventData: EventData) {
                entity[sm].set(BobComponent.STATE_JUMP)
            }

            override fun update(entity: Entity, machine: StateMachineSystem, delta: Float) {
                if (entity[tm].pos.y <= 0.5f) {
                    eventBus.emit(GameEvents.HIT_PLATFORM, entity)
                } else {
                    val mov = entity[mm]
                    mov.velocity.x = -accelX / 10.0f * BobComponent.MOVE_VELOCITY
                    if (mov.velocity.y < 0) {
                        eventBus.emit(GameEvents.PLAYER_FALLEN)
                    }
                }
                commonUpdate(entity)

            }
        }
        val STATE_FALL = object : EntityState() {

            override fun enter(entity: Entity, machine: StateMachineSystem, eventData: EventData) {
                entity[sm].set(BobComponent.STATE_FALL)
            }

            override fun update(entity: Entity, machine: StateMachineSystem, delta: Float) {
                if (entity[tm].pos.y <= 0.5f) {
                    eventBus.emit(GameEvents.HIT_PLATFORM, entity)
                } else {
                    entity[mm].velocity.x = -accelX / 10.0f * BobComponent.MOVE_VELOCITY
                }
                commonUpdate(entity)
            }
        }


        val STATE_HIT = object : EntityState() {
            override fun enter(entity: Entity, machine: StateMachineSystem, eventData: EventData) {
                entity[sm].set(BobComponent.STATE_HIT)
            }

            override fun update(entity: Entity, machine: StateMachineSystem, delta: Float) {
                commonUpdate(entity)
            }
        }

        startWith(STATE_FALL)
        onState(STATE_FALL).on(GameEvents.HIT_PLATFORM) { entity, _ ->
            hitPlatform(entity)
            go(STATE_JUMP, entity)
        }
        onState(STATE_FALL).on(GameEvents.HIT_SPRING) { entity, _ ->
            hitSpring(entity)
            go(STATE_JUMP, entity)
        }
        onState(STATE_FALL).on(GameEvents.HIT_SQUIRREL) { entity, _ ->
            hitSquirrel(entity)
            go(STATE_HIT, entity)
        }
        onState(STATE_JUMP).on(GameEvents.HIT_PLATFORM) { entity, _ ->
            hitPlatform(entity)
            go(STATE_JUMP, entity)
        }
        onState(STATE_JUMP).on(GameEvents.HIT_SPRING) { entity, _ ->
            hitSpring(entity)
            go(STATE_JUMP, entity)
        }
        onState(STATE_JUMP).on(GameEvents.HIT_SQUIRREL) { entity, _ ->
            hitSquirrel(entity)
            go(STATE_HIT, entity)
        }


    }


    private fun commonUpdate(entity: Entity) {
        val t = tm.get(entity)
        val mov = mm.get(entity)
        val bob = bm.get(entity)

        if (t.pos.x < 0) {
            t.pos.x = World.WORLD_WIDTH
        }

        if (t.pos.x > World.WORLD_WIDTH) {
            t.pos.x = 0f
        }

        t.scale.x = if (mov.velocity.x < 0.0f) Math.abs(t.scale.x) * -1.0f else Math.abs(t.scale.x)

        bob.heightSoFar = Math.max(t.pos.y, bob.heightSoFar)

        if (bob.heightSoFar - 7.5f > t.pos.y) {
            world.state = World.WORLD_STATE_GAME_OVER
        }
    }

    fun hitSquirrel(entity: Entity) {
        if (!family.matches(entity)) return

        val state = sm.get(entity)
        val mov = mm.get(entity)

        mov.velocity.set(0f, 0f)
        state.set(BobComponent.STATE_HIT)
    }

    fun hitPlatform(entity: Entity) {
        if (!family.matches(entity)) return

        val state = sm.get(entity)
        val mov = mm.get(entity)

        mov.velocity.y = BobComponent.JUMP_VELOCITY
        state.set(BobComponent.STATE_JUMP)
    }

    fun hitSpring(entity: Entity) {
        if (!family.matches(entity)) return

        val state = sm.get(entity)
        val mov = mm.get(entity)

        mov.velocity.y = BobComponent.JUMP_VELOCITY * 1.5f
        state.set(BobComponent.STATE_JUMP)
    }


}