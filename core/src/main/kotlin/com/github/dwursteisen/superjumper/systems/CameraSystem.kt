package com.github.dwursteisen.superjumper.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.github.dwursteisen.libgdx.ashley.get
import com.github.dwursteisen.libgdx.ashley.getNullable
import com.siondream.superjumper.components.CameraComponent
import com.siondream.superjumper.components.TransformComponent


class CameraSystem : IteratingSystem(Family.all(CameraComponent::class.java).get()) {

    private val tm: ComponentMapper<TransformComponent> = get()
    private val cm: ComponentMapper<CameraComponent> = get()

    public override fun processEntity(entity: Entity, deltaTime: Float) {
        val cam = entity[cm]

        if (cam.target == null) {
            return
        }

        val target = cam.target.getNullable(tm) ?: return

        cam.camera.position.y = Math.max(cam.camera.position.y, target.pos.y)
    }
}