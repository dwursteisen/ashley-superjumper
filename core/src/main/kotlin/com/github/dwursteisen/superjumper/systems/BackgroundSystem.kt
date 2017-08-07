package com.github.dwursteisen.superjumper.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.github.dwursteisen.libgdx.ashley.get
import com.siondream.superjumper.components.BackgroundComponent
import com.siondream.superjumper.components.TransformComponent

class BackgroundSystem : IteratingSystem(Family.all(BackgroundComponent::class.java).get()) {
    private var camera: OrthographicCamera? = null
    private val tm: ComponentMapper<TransformComponent> = get()

    fun setCamera(camera: OrthographicCamera) {
        this.camera = camera
    }

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[tm].pos.set(camera!!.position.x, camera!!.position.y, 10.0f)
    }
}
