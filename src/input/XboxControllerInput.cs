using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;

namespace XboxControllerInput
{
    /// <summary>
    /// This is the main type for your game
    /// </summary>
    public class XboxControllerInput : Microsoft.Xna.Framework.Game
    {
       // GraphicsDeviceManager graphics;
        //SpriteBatch spriteBatch;

        public XboxControllerInput()
        {
            //graphics = new GraphicsDeviceManager(this);
            GamePadState currentState = GamePad.GetState(PlayerIndex.One);
            vibrationAmount = MathHelper.Clamp(vibrationAmount + 0.03f, .3f, 2.0f);
            GamePad.SetVibration(PlayerIndex.One, vibrationAmount, vibrationAmount);
            Content.RootDirectory = "Content";

        }

        /// <summary>
        /// Allows the game to perform any initialization it needs to before starting to run.
        /// This is where it can query for any required services and load any non-graphic
        /// related content.  Calling base.Initialize will enumerate through any components
        /// and initialize them as well.
        /// </summary>sdfsdfj;lj
        protected override void Initialize()
        {
            // TODO: Add your initialization logic here
           
            base.Initialize();
        }

        /// <summary>
        /// LoadContent will be called once per game and is the place to load
        /// all of your content.
        /// </summary>
        protected override void LoadContent()
        {
            // Create a new SpriteBatch, which can be used to draw textures.
          //  spriteBatch = new SpriteBatch(GraphicsDevice);

            // TODO: use this.Content to load your game content here
        }

        /// <summary>
        /// UnloadContent will be called once per game and is the place to unload
        /// all content.
        /// </summary>
        protected override void UnloadContent()
        {
            // TODO: Unload any non ContentManager content here
        }

        /// <summary>
        /// Allows the game to run logic such as updating the world,
        /// checking for collisions, gathering input, and playing audio.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
        protected override void Update(GameTime gameTime)
        {
            // Allows the game to exit
            if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                this.Exit();
            UpdateInput();
            
            // TODO: Add your update logic here

            base.Update(gameTime);
        }

        float vibrationAmount = 0.0f;
        public void UpdateInput() {
            // Get the current gamepad state.
            GamePadState currentState = GamePad.GetState(PlayerIndex.One);
            int buttons=0;
            
            int leftTrigger=0;
            int rightTrigger=0;
       
            if((currentState.IsConnected && currentState.Buttons.B == ButtonState.Pressed)) {
                buttons = 1;
                Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
                 
              
                
               
               
            }
            if ((currentState.IsConnected && currentState.Buttons.Y == ButtonState.Pressed))
            {
                buttons = 2;
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
            if ((currentState.IsConnected && currentState.Buttons.X == ButtonState.Pressed))
            {
                buttons = 3;
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
            if ((currentState.IsConnected && currentState.Buttons.A == ButtonState.Pressed))
            {
                buttons = 4;
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
            if ((currentState.IsConnected && currentState.Buttons.RightShoulder == ButtonState.Pressed))
            {
                buttons = 5;
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
            if ((currentState.IsConnected && currentState.Buttons.LeftShoulder == ButtonState.Pressed))
            {
                buttons = 6;
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
             if ((currentState.IsConnected && currentState.Buttons.LeftStick == ButtonState.Pressed))
            {
                buttons = 8;
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
             if ((currentState.IsConnected && currentState.Buttons.RightStick == ButtonState.Pressed))
            {
                buttons = 7;
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
             if ((currentState.IsConnected && currentState.Buttons.Start == ButtonState.Pressed))
             {
                 buttons = 9;
                   Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
               
                 
                
                
             }
             if ((currentState.IsConnected && currentState.DPad.Right== ButtonState.Pressed))
             {
                 buttons = 10;
                   Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
               
                 
                
                
             }
             if ((currentState.IsConnected && currentState.DPad.Up== ButtonState.Pressed))
             {
                 buttons = 11;
                   Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
               
                 
                
                
             }
             if ((currentState.IsConnected && currentState.DPad.Left == ButtonState.Pressed))
             {
                 buttons = 12;
                   Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
               
                 
                
                
             }
             if ((currentState.IsConnected && currentState.DPad.Down== ButtonState.Pressed))
             {
                 buttons = 13;
                   Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
               
                 
                
                
             }    
            if (currentState.ThumbSticks.Left.X != 0 || currentState.ThumbSticks.Left.Y != 0)
            {
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
            if (currentState.ThumbSticks.Right.X != 0 || currentState.ThumbSticks.Right.Y != 0)
            {
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
            if (currentState.Triggers.Right != 0)
            {
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
            if (currentState.Triggers.Left != 0)
            {
                  Console.WriteLine(buttons + "," + getLeftStick() + "," + getRightStick() + "," + getLeftTrigger() + "," + getRightTrigger());
              
                
               
               
            }
           
                vibrationAmount =
                    MathHelper.Clamp(vibrationAmount - 0.05f, 0.0f, 1.0f);
                GamePad.SetVibration(PlayerIndex.One,
                    vibrationAmount, vibrationAmount);               
            }
        public String getLeftTrigger()
        {
            float LeftTrigger=0;
            GamePadState currentState = GamePad.GetState(PlayerIndex.One);
            if (currentState.Triggers.Left != 0)
            {
                LeftTrigger = currentState.Triggers.Left;
            }
            return LeftTrigger.ToString();
        }
        public String getRightTrigger()
        {
            float RightTrigger = 0;
            GamePadState currentState = GamePad.GetState(PlayerIndex.One);
            if (currentState.Triggers.Right != 0)
            {
                RightTrigger = currentState.Triggers.Right;
            }
            return RightTrigger.ToString();
        }
        public String getLeftStick()
        {
            Vector2 LeftStick = new Vector2(0, 0);
            
            GamePadState currentState = GamePad.GetState(PlayerIndex.One);
            if (currentState.ThumbSticks.Left.X != 0 || currentState.ThumbSticks.Left.Y != 0)
            {
                LeftStick.X = currentState.ThumbSticks.Left.X;
                LeftStick.Y = currentState.ThumbSticks.Left.Y;
            }

            return LeftStick.ToString();
        }
        public String getRightStick()
        {
            Vector2 RightStick = new Vector2(0, 0);

            GamePadState currentState = GamePad.GetState(PlayerIndex.One);
            if (currentState.ThumbSticks.Right.X != 0 || currentState.ThumbSticks.Right.Y != 0)
            {
                RightStick.X = currentState.ThumbSticks.Right.X;
                RightStick.Y = currentState.ThumbSticks.Right.Y;
            }

            return RightStick.ToString();
        }

        /// <summary>
        /// This is called when the game should draw itself.
        /// </summary>
        /// <param name="gameTime">Provides a snapshot of timing values.</param>
      //  protected override void Draw(GameTime gameTime)
        //{
            //GraphicsDevice.Clear(Color.CornflowerBlue);

            // TODO: Add your drawing code here

          //  base.Draw(gameTime);
       // }
    }
}
