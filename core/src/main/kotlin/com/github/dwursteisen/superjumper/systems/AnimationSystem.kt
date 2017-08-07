package com.github.dwursteisen.superjumper.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.libgdx.ashley.StateComponent
import com.github.dwursteisen.libgdx.ashley.get
import com.siondream.superjumper.components.AnimationComponent
import com.siondream.superjumper.components.TextureComponent


class AnimationSystem : IteratingSystem(Family.all(TextureComponent::class.java,
        AnimationComponent::class.java,
        StateComponent::class.java).get()) {

    private val tm: ComponentMapper<TextureComponent> = get()
    private val am: ComponentMapper<AnimationComponent> = get()
    private val sm: ComponentMapper<StateComponent> = get()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val tex = entity[tm]
        val anim = entity[am]
        val state = entity[sm]

        val animation = anim.animations.get(state.get())

        if (animation != null) {
            tex.region = animation.getKeyFrame(state.time)
        }

        state.time += deltaTime
    }
}