package dev.skydynamic.pearltickets.mixin;

import dev.skydynamic.pearltickets.config.Config;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
// import net.minecraft.server.world.ChunkLevelType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin extends ThrownItemEntity
{
	@Unique
	private static final ChunkTicketType<ChunkPos> ENDER_PEARL_TICKET = ChunkTicketType.create("block_loader", Comparator.comparingLong(ChunkPos::toLong), 2);

	protected EnderPearlEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world)
	{
		super(entityType, world);
	}

	@Unique
	private final boolean sync = true;
	@Unique
	private Vec3d realPos = null;
	@Unique
	private Vec3d realVelocity = null;
	@Unique
	private boolean enableLoading = false;

	@Unique
	private Vec3d GetEnderPearlPos() {
		return this.getPos();
	}

	@Unique
	private Vec3d GetEnderPearlVelocity() {
		return this.getVelocity();
	}

	@Inject(method = "tick", at = @At(value = "HEAD"))
	private void ChunkLoading(CallbackInfo ci)
	{
		World world = this.getEntityWorld();

		if (world instanceof ServerWorld && Config.INSTANCE.containsTrue())
		{
			Vec3d currPos = GetEnderPearlPos().add(Vec3d.ZERO);
			Vec3d currVelocity = GetEnderPearlVelocity().add(Vec3d.ZERO);

			WorldChunk currChunk = world.getChunk((int) Math.floor(currPos.x / 16), (int) Math.floor(currPos.z / 16));

			if (this.sync) {
				this.realPos = currPos;
				this.realVelocity = currVelocity;
				this.enableLoading = true;
			}

			Vec3d nextPos = this.realPos.add(this.realVelocity);
			// Vec3d nextVelocity = this.realVelocity.multiply(0.99F).subtract(0, this.getGravity(), 0);

			WorldChunk nextChunk = world.getChunk((int) Math.floor(nextPos.x / 16), (int) Math.floor(nextPos.z / 16));

			// GetEnderPearlOwner().sendMessage(Text.of("珍珠下一Tick区块: " + nextChunk.getPos().toString()));

			if (enableLoading) {
				((ServerWorld) world).getChunkManager().addTicket(ENDER_PEARL_TICKET, currChunk.getPos(), 2, currChunk.getPos());
				((ServerWorld) world).getChunkManager().addTicket(ENDER_PEARL_TICKET, nextChunk.getPos(), 2, nextChunk.getPos());
			}
		}
	}

	@Inject(method = "onCollision", at = @At(value = "RETURN"))
	private void StopChunkLoading(CallbackInfo ci)
	{
		this.enableLoading = false;
	}
}