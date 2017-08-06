package com.github.dwursteisen.superjumper

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.github.dwursteisen.libgdx.ashley.StateComponent
import com.github.dwursteisen.libgdx.ashley.createComponent
import com.github.dwursteisen.libgdx.ashley.createComponentWith
import com.siondream.superjumper.components.*
import com.siondream.superjumper.systems.RenderingSystem
import java.util.*


class World(private val engine: PooledEngine) {

    private val rand: Random = Random()

    var heightSoFar: Float = 0.toFloat()
    @JvmField
    var score: Int = 0
    @JvmField
    var state: Int = 0

    fun create() {
        val bob = createBob()
        createCamera(bob)
        createBackground()
        generateLevel()

        this.heightSoFar = 0f
        this.score = 0
        this.state = WORLD_STATE_RUNNING
    }

    private fun generateLevel() {
        var y = PlatformComponent.HEIGHT / 2
        val maxJumpHeight = BobComponent.JUMP_VELOCITY * BobComponent.JUMP_VELOCITY / (2 * -gravity.y)
        while (y < WORLD_HEIGHT - WORLD_WIDTH / 2) {
            val type = if (rand.nextFloat() > 0.8f) PlatformComponent.TYPE_MOVING else PlatformComponent.TYPE_STATIC
            val x = rand.nextFloat() * (WORLD_WIDTH - PlatformComponent.WIDTH) + PlatformComponent.WIDTH / 2

            createPlatform(type, x, y)

            if (rand.nextFloat() > 0.9f && type != PlatformComponent.TYPE_MOVING) {
                createSpring(x, y + PlatformComponent.HEIGHT / 2 + SpringComponent.HEIGHT / 2)
            }

            if (y > WORLD_HEIGHT / 3 && rand.nextFloat() > 0.8f) {
                createSquirrel(x + rand.nextFloat(), y + SquirrelComponent.HEIGHT + rand.nextFloat() * 2)
            }

            if (rand.nextFloat() > 0.6f) {
                createCoin(x + MathUtils.random(-0.5f, 0.5f), y + CoinComponent.HEIGHT + rand.nextFloat() * 3)
            }

            y += maxJumpHeight - 0.5f
            y -= rand.nextFloat() * (maxJumpHeight / 3)
        }

        createCastle(WORLD_WIDTH / 2, y)
    }

    private fun createBob(): Entity {
        val entity = engine.createEntity()

        val animation: AnimationComponent = engine.createComponentWith {
            animations.put(BobComponent.STATE_FALL, Assets.bobFall)
            animations.put(BobComponent.STATE_HIT, Assets.bobHit)
            animations.put(BobComponent.STATE_JUMP, Assets.bobJump)
        }

        val bob: BobComponent = engine.createComponent()

        val bounds: BoundsComponent = engine.createComponentWith {
            bounds.width = BobComponent.WIDTH
            bounds.height = BobComponent.HEIGHT
        }
        val gravity: GravityComponent = engine.createComponent()
        val movement: MovementComponent = engine.createComponent()
        val position: TransformComponent = engine.createComponentWith {
            pos.set(5.0f, 1.0f, 0.0f)
        }
        val state: StateComponent = engine.createComponentWith {
            set(BobComponent.STATE_JUMP)
        }

        val texture: TextureComponent = engine.createComponent()

        entity.add(animation)
        entity.add(bob)
        entity.add(bounds)
        entity.add(gravity)
        entity.add(movement)
        entity.add(position)
        entity.add(state)
        entity.add(texture)

        engine.addEntity(entity)

        return entity
    }

    private fun createPlatform(type: Int, x: Float, y: Float) {
        val entity = Entity()//engine.createEntity();

        val animation: AnimationComponent = engine.createComponentWith {
            animations.put(PlatformComponent.STATE_NORMAL, Assets.platform)
            animations.put(PlatformComponent.STATE_PULVERIZING, Assets.breakingPlatform)

        }

        val platform: PlatformComponent = engine.createComponentWith {
            this.type = type
        }

        val bounds: BoundsComponent = engine.createComponentWith {
            bounds.width = PlatformComponent.WIDTH
            bounds.height = PlatformComponent.HEIGHT
        }

        val movement: MovementComponent = engine.createComponentWith {
            if (type == PlatformComponent.TYPE_MOVING) {
                velocity.x = if (rand.nextBoolean()) PlatformComponent.VELOCITY else -PlatformComponent.VELOCITY
            }
        }

        val position: TransformComponent = engine.createComponentWith {
            pos.set(x, y, 1.0f)
        }

        val state: StateComponent = engine.createComponentWith {
            set(PlatformComponent.STATE_NORMAL)
        }

        val texture: TextureComponent = engine.createComponent()

        entity.add(animation)
        entity.add(platform)
        entity.add(bounds)
        entity.add(movement)
        entity.add(position)
        entity.add(state)
        entity.add(texture)

        engine.addEntity(entity)
    }

    private fun createSpring(x: Float, y: Float) {
        val entity = engine.createEntity()

        val spring: SpringComponent = engine.createComponent()
        val bounds: BoundsComponent = engine.createComponent()
        val position: TransformComponent = engine.createComponent()
        val texture: TextureComponent = engine.createComponent()

        bounds.bounds.width = SpringComponent.WIDTH
        bounds.bounds.height = SpringComponent.HEIGHT

        position.pos.set(x, y, 2.0f)

        texture.region = Assets.spring

        entity.add(spring)
        entity.add(bounds)
        entity.add(position)
        entity.add(texture)

        engine.addEntity(entity)
    }

    private fun createSquirrel(x: Float, y: Float) {
        val entity = engine.createEntity()

        val animation: AnimationComponent = engine.createComponent()
        val squirrel: SquirrelComponent = engine.createComponent()
        val bounds: BoundsComponent = engine.createComponent()
        val movement: MovementComponent = engine.createComponent()
        val position: TransformComponent = engine.createComponent()
        val state: StateComponent = engine.createComponent()
        val texture: TextureComponent = engine.createComponent()

        movement.velocity.x = if (rand.nextFloat() > 0.5f) SquirrelComponent.VELOCITY else -SquirrelComponent.VELOCITY

        animation.animations.put(SquirrelComponent.STATE_NORMAL, Assets.squirrelFly)

        bounds.bounds.width = SquirrelComponent.WIDTH
        bounds.bounds.height = SquirrelComponent.HEIGHT

        position.pos.set(x, y, 2.0f)

        state.set(SquirrelComponent.STATE_NORMAL)

        entity.add(animation)
        entity.add(squirrel)
        entity.add(bounds)
        entity.add(movement)
        entity.add(position)
        entity.add(state)
        entity.add(texture)

        engine.addEntity(entity)
    }

    private fun createCoin(x: Float, y: Float) {
        val entity = engine.createEntity()

        val animation: AnimationComponent = engine.createComponent()
        val coin: CoinComponent = engine.createComponent()
        val bounds: BoundsComponent = engine.createComponent()
        val position: TransformComponent = engine.createComponent()
        val state: StateComponent = engine.createComponent()
        val texture: TextureComponent = engine.createComponent()

        animation.animations.put(CoinComponent.STATE_NORMAL, Assets.coinAnim)

        bounds.bounds.width = CoinComponent.WIDTH
        bounds.bounds.height = CoinComponent.HEIGHT

        position.pos.set(x, y, 3.0f)

        state.set(CoinComponent.STATE_NORMAL)

        entity.add(coin)
        entity.add(bounds)
        entity.add(position)
        entity.add(texture)
        entity.add(animation)
        entity.add(state)

        engine.addEntity(entity)
    }

    private fun createCastle(x: Float, y: Float) {
        val entity = engine.createEntity()

        val castle: CastleComponent = engine.createComponent()
        val bounds: BoundsComponent = engine.createComponent()
        val position: TransformComponent = engine.createComponent()
        val texture: TextureComponent = engine.createComponent()

        bounds.bounds.width = CastleComponent.WIDTH
        bounds.bounds.height = CastleComponent.HEIGHT

        position.pos.set(x, y, 2.0f)

        texture.region = Assets.castle

        entity.add(castle)
        entity.add(bounds)
        entity.add(position)
        entity.add(texture)

        engine.addEntity(entity)
    }

    private fun createCamera(target: Entity) {
        val entity = engine.createEntity()

        val camera = CameraComponent()
        camera.camera = engine.getSystem(RenderingSystem::class.java).camera
        camera.target = target

        entity.add(camera)

        engine.addEntity(entity)
    }

    private fun createBackground() {
        val entity = engine.createEntity()

        val background: BackgroundComponent = engine.createComponent()
        val position: TransformComponent = engine.createComponent()
        val texture: TextureComponent = engine.createComponent()

        texture.region = Assets.backgroundRegion

        entity.add(background)
        entity.add(position)
        entity.add(texture)

        engine.addEntity(entity)
    }

    companion object {
        @JvmField
        val WORLD_WIDTH = 10f
        @JvmField
        val WORLD_HEIGHT = (15 * 20).toFloat()
        @JvmField
        val WORLD_STATE_RUNNING = 0
        @JvmField
        val WORLD_STATE_NEXT_LEVEL = 1
        @JvmField
        val WORLD_STATE_GAME_OVER = 2

        @JvmField
        val gravity = Vector2(0f, -12f)
    }
}