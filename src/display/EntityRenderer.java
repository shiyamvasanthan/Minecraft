package display;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.TexturedModel;
import shaders.StaticShader;
import toolbox.Maths;

//This class is responsible to rendering all the models
public class EntityRenderer {
	StaticShader shader = new StaticShader();
	
	public void render (Map<TexturedModel, List<Entity>> entities) {
		for (TexturedModel model : entities.keySet()) {
			//Bind VAO so it can be used
			GL30.glBindVertexArray(model.getModel().getVaoID());
			
			//Enable VAO number 0, so we can access vertice coordinates
			GL20.glEnableVertexAttribArray(0);
			
			//Enable VAO number 1, so we can access texture coordinates
			GL20.glEnableVertexAttribArray(1);
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
		
			List<Entity> batch = entities.get(model);
			
			for (Entity entity : batch) {
				Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
				shader.loadTransformationMatrix(transformationMatrix);
				GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getModel().getVertexCount());
			}
			
			//Disable VAO number 0 after we render it
			GL20.glDisableVertexAttribArray(0);
			
			//Disable VAO number 1 after we render it
			GL20.glDisableVertexAttribArray(1);
			
			//Unbind the VAO
			GL30.glBindVertexArray(0);
		}
	}
}
